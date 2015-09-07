package productcatalog.pages;

import common.pages.LinkData;
import io.sphere.sdk.models.Base;

import java.util.List;

public class PaginationData extends Base {
    private final int currentPage;
    private final int totalPages;
    private List<LinkData> pages;
    private LinkData prevPage;
    private LinkData nextPage;
    private LinkData firstPage;
    private LinkData lastPage;

    public PaginationData(final int currentPage, final int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<LinkData> getPages() {
        return pages;
    }

    public void setPages(final List<LinkData> pages) {
        this.pages = pages;
    }

    public LinkData getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(final LinkData prevPage) {
        this.prevPage = prevPage;
    }

    public LinkData getNextPage() {
        return nextPage;
    }

    public void setNextPage(final LinkData nextPage) {
        this.nextPage = nextPage;
    }

    public LinkData getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(final LinkData firstPage) {
        this.firstPage = firstPage;
    }

    public LinkData getLastPage() {
        return lastPage;
    }

    public void setLastPage(final LinkData lastPage) {
        this.lastPage = lastPage;
    }
}
