package productcatalog.pages;

import common.pages.LinkData;
import common.pages.PageContent;

import java.util.List;

public class ProductOverviewPageContent extends PageContent {
    private final String additionalTitle;
    private final ProductOverviewPageStaticData staticData;
    private final List<LinkData> breadcrumb;
    private final ProductListData productListData;
    private final FilterListData filterListData;
    private final PaginationData paginationData;

    public ProductOverviewPageContent(final String additionalTitle, final ProductOverviewPageStaticData staticData,
                                      final List<LinkData> breadcrumb, final ProductListData productListData,
                                      final FilterListData filterListData, final PaginationData paginationData) {
        this.additionalTitle = additionalTitle;
        this.staticData = staticData;
        this.breadcrumb = breadcrumb;
        this.productListData = productListData;
        this.filterListData = filterListData;
        this.paginationData = paginationData;
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

    public PaginationData getPagination() {
        return paginationData;
    }
}