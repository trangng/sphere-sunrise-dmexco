package common.pages;

import io.sphere.sdk.models.Base;


public class MiniCartData extends Base {

    final String url;
    final int numItems;

    public MiniCartData(final String url, final int numItems) {
        this.url = url;
        this.numItems = numItems;
    }

    public String getUrl() {
        return url;
    }

    public int getNumItems() {
        return numItems;
    }
}
