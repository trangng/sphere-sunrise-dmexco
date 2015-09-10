package productcatalog.controllers;

import common.cms.CmsPage;
import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import common.pages.LinkData;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.facets.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.search.*;
import play.Configuration;
import play.Logger;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Result;
import productcatalog.models.SortOption;
import productcatalog.models.SortOptionImpl;
import productcatalog.pages.*;
import productcatalog.services.CategoryService;
import productcatalog.services.ProductProjectionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

import static io.sphere.sdk.facets.DefaultFacetType.HIERARCHICAL_SELECT;
import static io.sphere.sdk.facets.DefaultFacetType.SORTED_SELECT;
import static io.sphere.sdk.search.SimpleSearchSortDirection.ASC;
import static io.sphere.sdk.search.SimpleSearchSortDirection.DESC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Singleton
public class ProductOverviewPageController extends SunriseController {
    private static final StringSearchModel<ProductProjection, ?> BRAND_SEARCH_MODEL = ProductProjectionSearchModel.of().allVariants().attribute().ofEnum("designer").label();
    private static final StringSearchModel<ProductProjection, ?> COLOR_SEARCH_MODEL = ProductProjectionSearchModel.of().allVariants().attribute().ofLocalizableEnum("color").key();
    private static final StringSearchModel<ProductProjection, ?> SIZE_SEARCH_MODEL = ProductProjectionSearchModel.of().allVariants().attribute().ofEnum("commonSize").label();
    private static final StringSearchModel<ProductProjection, ?> CATEGORY_SEARCH_MODEL = ProductProjectionSearchModel.of().categories().id();
    private static final SearchSort<ProductProjection> MODIFIED_SORT_BY_DESC = ProductProjectionSearchModel.of().lastModifiedAt().sorted(DESC);
    private static final SearchSort<ProductProjection> PRICE_SORT_BY_DESC = ProductProjectionSearchModel.of().allVariants().price().centAmount().sorted(DESC);
    private static final SearchSort<ProductProjection> PRICE_SORT_BY_ASC = ProductProjectionSearchModel.of().allVariants().price().centAmount().sorted(ASC);
    private static final String FACET_COLOR_KEY = "color";
    private static final String FACET_SIZE_KEY = "size";
    private static final String FACET_CATEGORY_KEY = "productType";
    private static final String FACET_BRAND_KEY = "brands";
    private static final String SORT_NEW_KEY = "new";
    private static final String SORT_PRICE_ASC_KEY = "price-asc";
    private static final String SORT_PRICE_DESC_KEY = "price-desc";
    private final int pageSize;
    private final int displayedPages;
    private final ProductProjectionService productService;
    private final CategoryService categoryService;

    @Inject
    public ProductOverviewPageController(final Configuration configuration, final ControllerDependency controllerDependency,
                                         final ProductProjectionService productService, final CategoryService categoryService) {
        super(controllerDependency);
        this.productService = productService;
        this.categoryService = categoryService;
        this.pageSize = configuration.getInt("pop.pageSize");
        this.displayedPages = configuration.getInt("pop.displayedPages");
    }

    public F.Promise<Result> show(final String locale, final String categorySlug, final int page) {
        final UserContext userContext = userContext(locale);
        final Messages messages = messages(userContext);
        final Optional<Category> category = categories().findBySlug(userContext.locale(), categorySlug);
        if (category.isPresent()) {
            final List<Category> childrenCategories = categories().findChildren(category.get());
            final List<Facet<ProductProjection>> boundFacets = boundFacetList(userContext.locale(), childrenCategories, messages);
            final List<SortOption<ProductProjection>> boundSortOptions = boundSortOptionList(messages);
            final F.Promise<PagedSearchResult<ProductProjection>> searchResultPromise = searchProducts(category.get(), page, boundFacets, boundSortOptions);
            final F.Promise<CmsPage> cmsPromise = cmsService().getPage(userContext.locale(), "pop");
            return searchResultPromise.flatMap(searchResult ->
                            cmsPromise.map(cms -> {
                                final ProductOverviewPageContent content = getPopPageData(cms, userContext, messages, searchResult, boundFacets, page, category.get().toReference());
                                return ok(templateService().renderToHtml("pop", pageData(userContext, content)));
                            })
            );
        }
        return F.Promise.pure(notFound("Category not found: " + categorySlug));
    }

    private List<Facet<ProductProjection>> boundFacetList(final Locale locale, final List<Category> childrenCategories, final Messages messages) {
        final List<Category> subcategories = getCategoriesAsFlatList(categories(), childrenCategories);
        final FacetOptionMapper categoryHierarchyMapper = HierarchicalCategoryFacetOptionMapper.of(subcategories, singletonList(locale));
        final FacetOptionMapper sortedColorFacetOptionMapper = SortedFacetOptionMapper.of(emptyList());
        final FacetOptionMapper sortedSizeFacetOptionMapper = SortedFacetOptionMapper.of(emptyList());
        final List<Facet<ProductProjection>> facets = asList(
                FlexibleSelectFacetBuilder.of(FACET_CATEGORY_KEY, messages.at("pop.facetProductType"), HIERARCHICAL_SELECT, CATEGORY_SEARCH_MODEL, categoryHierarchyMapper).build(),
                FlexibleSelectFacetBuilder.of(FACET_SIZE_KEY, messages.at("pop.facetSize"), SORTED_SELECT, SIZE_SEARCH_MODEL, sortedSizeFacetOptionMapper).countHidden(true).build(),
                FlexibleSelectFacetBuilder.of(FACET_COLOR_KEY, messages.at("pop.facetColor"), SORTED_SELECT, COLOR_SEARCH_MODEL, sortedColorFacetOptionMapper).build(),
                SelectFacetBuilder.of(FACET_BRAND_KEY, messages.at("pop.facetBrand"), BRAND_SEARCH_MODEL).build());
        return bindFacetsWithRequest(facets);
    }

    private List<SortOption<ProductProjection>> boundSortOptionList(final Messages messages) {
        final List<SortOption<ProductProjection>> sortOptions = asList(
                SortOptionImpl.of(messages.at("pop.sortNew"), SORT_NEW_KEY, true, MODIFIED_SORT_BY_DESC),
                SortOptionImpl.of(messages.at("pop.sortPriceAsc"), SORT_PRICE_ASC_KEY, false, PRICE_SORT_BY_ASC),
                SortOptionImpl.of(messages.at("pop.sortPriceDesc"), SORT_PRICE_DESC_KEY, false, PRICE_SORT_BY_DESC));
        return bindSortOptionsWithRequest(sortOptions);
    }

    private ProductOverviewPageContent getPopPageData(final CmsPage cms, final UserContext userContext, final Messages messages,
                                                      final PagedSearchResult<ProductProjection> searchResult,
                                                      final List<Facet<ProductProjection>> boundFacets, final int currentPage,
                                                      final Reference<Category> category) {
        final String additionalTitle = "";
        final ProductOverviewPageStaticData staticData = new ProductOverviewPageStaticData(messages(userContext));
        final List<LinkData> breadcrumbData = getBreadcrumbData(userContext, category);
        final ProductListData productListData = getProductListData(searchResult.getResults(), userContext);
        final FilterListData filterListData = getFilterListData(searchResult, boundFacets);
        final PaginationData paginationData = getPaginationData(searchResult, currentPage);
        final List<SortOption<ProductProjection>> sortingData = boundSortOptionList(messages);
        return new ProductOverviewPageContent(additionalTitle, staticData, breadcrumbData, productListData, filterListData, sortingData, paginationData);
    }

    /* Maybe move to some common controller class */

    private static <T> List<Facet<T>> bindFacetsWithRequest(final List<Facet<T>> facets) {
        return facets.stream().map(facet -> {
            final List<String> selectedValues = asList(request().queryString().getOrDefault(facet.getKey(), new String[0]));
            return facet.withSelectedValues(selectedValues);
        }).collect(toList());
    }

    private static <T> List<SortOption<T>> bindSortOptionsWithRequest(final List<SortOption<T>> sortOptions) {
        final String sortCriteria = Optional.ofNullable(request().getQueryString("sort")).orElse("");
        return sortOptions.stream()
                .map(option -> {
                    final boolean isSelected = sortCriteria.equals(option.getValue());
                    return option.withSelected(isSelected);
                })
                .collect(toList());
    }

    /* Move to product service */

    private F.Promise<PagedSearchResult<ProductProjection>> searchProducts(final Category category, final int page,
                                                                           final List<Facet<ProductProjection>> boundFacets,
                                                                           final List<SortOption<ProductProjection>> sortOptions) {
        final int offset = (page - 1) * pageSize;
        final List<String> categoriesId = getCategoriesAsFlatList(categories(), singletonList(category)).stream().map(Category::getId).collect(toList());
        final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofCurrent()
                .withQueryFilters(model -> model.categories().id().filtered().by(categoriesId))
                .withOffset(offset)
                .withLimit(pageSize);
        final ProductProjectionSearch facetedSearchRequest = getFacetedSearchRequest(searchRequest, boundFacets);
        final ProductProjectionSearch sortedFacetedSearchRequest = getSortedSearchRequest(facetedSearchRequest, sortOptions);
        final F.Promise<PagedSearchResult<ProductProjection>> searchResultPromise = sphere().execute(sortedFacetedSearchRequest);
        searchResultPromise.onRedeem(result -> Logger.debug("Fetched {} out of {} products with request {}",
                result.size(),
                result.getTotal(),
                sortedFacetedSearchRequest.httpRequestIntent().getPath()));
        return searchResultPromise;
    }

    private static List<Category> getCategoriesAsFlatList(final CategoryTree categoryTree, final List<Category> parentCategories) {
        final List<Category> categories = new ArrayList<>();
        parentCategories.stream().forEach(parent -> {
            categories.add(parent);
            final List<Category> children = categoryTree.findChildren(parent);
            categories.addAll(getCategoriesAsFlatList(categoryTree, children));
        });
        return categories;
    }

    /* Maybe export it to generic FacetedSearch class */

    private static <T, S extends MetaModelSearchDsl<T, S, M, E>, M, E> S getFacetedSearchRequest(final S baseSearchRequest,
                                                                                                 final List<Facet<T>> facets) {
        S searchRequest = baseSearchRequest;
        for (final Facet<T> facet : facets) {
            final List<FilterExpression<T>> filterExpressions = facet.getFilterExpressions();
            searchRequest = searchRequest
                    .plusFacets(facet.getFacetExpression())
                    .plusFacetFilters(filterExpressions)
                    .plusResultFilters(filterExpressions);
        }
        return searchRequest;
    }

    private static <T, S extends MetaModelSearchDsl<T, S, M, E>, M, E> S getSortedSearchRequest(final S searchRequest,
                                                                                                final List<SortOption<T>> sortOptions) {
        return sortOptions.stream()
                .filter(SortOption::isSelected)
                .findFirst()
                .map(option -> searchRequest.withSort(option.getSortModel()))
                .orElse(searchRequest);
    }

    /* This will probably be moved to some kind of factory classes */

    private List<LinkData> getBreadcrumbData(final UserContext userContext, final Reference<Category> category) {
        final CategoryLinkDataFactory categoryLinkDataFactory = CategoryLinkDataFactory.of(reverseRouter(), userContext.locale());
        return categoryService.getBreadCrumbCategories(category).stream()
                .map(categoryLinkDataFactory::create)
                .collect(toList());
    }

    private ProductListData getProductListData(final List<ProductProjection> productList, final UserContext userContext) {
        final ProductDataFactory productDataFactory = ProductDataFactory.of(userContext, reverseRouter());
        final List<ProductData> productDataList = productList.stream()
                .map(product -> productDataFactory.create(product, product.getMasterVariant()))
                .collect(toList());
        return new ProductListData(productDataList);
    }

    private <T> FilterListData getFilterListData(final PagedSearchResult<T> searchResult, final List<Facet<T>> boundFacets) {
        final List<FacetData> facets = boundFacets.stream()
                .map(facet -> facet.withSearchResult(searchResult))
                .map(FacetData::new)
                .collect(toList());
        return new FilterListData(request().uri(), facets);
    }

    private PaginationData getPaginationData(final PagedSearchResult<ProductProjection> searchResult, int currentPage) {
        return new PaginationDataFactory(request(), searchResult, currentPage, pageSize, displayedPages).create();
    }
}