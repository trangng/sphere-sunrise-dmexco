package productcatalog.pages;

import common.pages.LinkData;
import common.pages.PageContent;
import common.pages.SelectableData;
import io.sphere.sdk.products.ProductProjection;
import productcatalog.models.SortOption;

import java.util.List;

public class ProductOverviewPageContent extends PageContent {
    private final String additionalTitle;
    private final ProductOverviewPageStaticData staticData;
    private final List<LinkData> breadcrumb;
    private final ProductListData productListData;
    private final FilterListData filterListData;
    private final List<SortOption<ProductProjection>> sortOptions;
    private final PaginationData paginationData;
    private final List<SelectableData> displayOptions;

    public ProductOverviewPageContent(final String additionalTitle, final ProductOverviewPageStaticData staticData,
                                      final List<LinkData> breadcrumb, final ProductListData productListData,
                                      final FilterListData filterListData, final List<SortOption<ProductProjection>> sortOptions,
                                      final PaginationData paginationData, final List<SelectableData> displayOptions) {
        this.additionalTitle = additionalTitle;
        this.staticData = staticData;
        this.breadcrumb = breadcrumb;
        this.productListData = productListData;
        this.filterListData = filterListData;
        this.sortOptions = sortOptions;
        this.paginationData = paginationData;
        this.displayOptions = displayOptions;
    }

    @Override
    public String additionalTitle() {
        return additionalTitle;
    }

    public ProductOverviewPageStaticData getStatic() {
        return staticData;
    }

    public List<LinkData> getBreadcrumb() {
        return breadcrumb;
    }

    public FilterListData getFilters() {
        return filterListData;
    }

    public ProductListData getProducts() {
        return productListData;
    }

    public List<SortOption<ProductProjection>> getSort() {
        return sortOptions;
    }

    public List<SelectableData> getDisplay() {
        return displayOptions;
    }

    public PaginationData getPagination() {
        return paginationData;
    }
}