package productcatalog.pages;

import common.pages.LinkData;
import io.sphere.sdk.models.Base;

import java.util.List;

public class PaginationData extends Base {
    private final String urlPrev;
    private final String urlNext;
    private final int prev;
    private final int next;
    private final List<LinkData> pages;

    public PaginationData(final String urlPrev, final String urlNext, final int prev, final int next, final List<LinkData> pages) {
        this.urlPrev = urlPrev;
        this.urlNext = urlNext;
        this.prev = prev;
        this.next = next;
        this.pages = pages;
    }

    public String getUrlPrev() {
        return urlPrev;
    }

    public String getUrlNext() {
        return urlNext;
    }

    public int getPrev() {
        return prev;
    }

    public int getNext() {
        return next;
    }

    public List<LinkData> getPages() {
        return pages;
    }
}
