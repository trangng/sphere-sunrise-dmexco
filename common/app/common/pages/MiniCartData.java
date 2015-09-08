package common.pages;

import io.sphere.sdk.models.Base;


public class MiniCartData extends Base {

    final String url;
    final Long numItems;

    public MiniCartData(final String url, final Long numItems) {
        this.url = url;
        this.numItems = numItems;
    }

    public String getUrl() {
        return url;
    }

    public Long getNumItems() {
        return numItems;
    }
}
