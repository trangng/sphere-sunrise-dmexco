package productcatalog.pages;

import io.sphere.sdk.models.Base;

import java.util.List;

public class FilterListData extends Base {
    private final List<FacetData> facetData;
    private final String url;

    public FilterListData(final String url, final List<FacetData> facetData) {
        this.url = url;
        this.facetData = facetData;
    }

    public String getUrl() {
        return url;
    }

    public List<FacetData> getList() {
        return facetData;
    }
}
