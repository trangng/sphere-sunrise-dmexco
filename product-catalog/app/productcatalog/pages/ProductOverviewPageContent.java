package productcatalog.pages;

import common.pages.PageContent;

public class ProductOverviewPageContent extends PageContent {
    private final String additionalTitle;
    private final ProductOverviewPageStaticData staticData;
    private final ProductListData productListData;
    private final FilterListData filterListData;
    private final PaginationData paginationData;

    public ProductOverviewPageContent(final String additionalTitle, final ProductOverviewPageStaticData staticData,
                                      final ProductListData productListData, final FilterListData filterListData,
                                      final PaginationData paginationData) {
        this.additionalTitle = additionalTitle;
        this.staticData = staticData;
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
