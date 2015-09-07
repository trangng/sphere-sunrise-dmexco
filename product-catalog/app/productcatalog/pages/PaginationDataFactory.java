package productcatalog.pages;

import common.pages.LinkData;
import io.sphere.sdk.models.Base;
import play.mvc.Http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static common.utils.UrlUtils.buildUrl;
import static java.util.stream.Collectors.toList;

public class PaginationDataFactory extends Base {
    private final Http.Request request;
    private final int currentPage;
    private final int totalPages;
    private final int displayedPages;
    private int pageThresholdLeft;
    private int pageThresholdRight;

    public PaginationDataFactory(final Http.Request request, final int currentPage, final int totalPages, final int displayedPages) {
        this.request = request;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.displayedPages = displayedPages;
        this.pageThresholdLeft = displayedPages - 1;
        this.pageThresholdRight = totalPages - displayedPages + 2;
    }

    public PaginationData create() {
        final PaginationData paginationData = new PaginationData(currentPage, totalPages);

        final List<LinkData> pages;
        if (totalPages <= displayedPages) {
            pages = getPages(1, totalPages);
        } else if (currentPage < pageThresholdLeft) {
            pages = getPages(1, pageThresholdLeft);
            paginationData.setLastPage(getLinkData(totalPages));
        } else if (currentPage > pageThresholdRight) {
            pages = getPages(pageThresholdRight, totalPages);
            paginationData.setFirstPage(getLinkData(1));
        } else {
            pages = getPages(currentPage - 1, currentPage + 1);
            paginationData.setFirstPage(getLinkData(1));
            paginationData.setLastPage(getLinkData(totalPages));
        }
        paginationData.setPages(pages);
        setPrevPage(paginationData);
        setNextPage(paginationData);
        return paginationData;
    }

    private void setNextPage(final PaginationData paginationData) {
        if (currentPage < totalPages) {
            paginationData.setNextPage(getLinkData(currentPage + 1));
        }
    }

    private void setPrevPage(final PaginationData paginationData) {
        if (currentPage > 1) {
            paginationData.setPrevPage(getLinkData(currentPage - 1));
        }
    }

    private List<LinkData> getPages(final int startPage, final int endPage) {
        return IntStream.rangeClosed(startPage, endPage)
                .mapToObj(this::getLinkData)
                .collect(toList());
    }

    private LinkData getLinkData(final int page) {
        return LinkData.of(String.valueOf(page), buildRequestUrlWithPage(page), page == currentPage);
    }

    private String buildRequestUrlWithPage(final int page) {
        final Map<String, String[]> queryString = new HashMap<>(request.queryString());
        queryString.put("page", new String[]{String.valueOf(page)});
        return buildUrl(request.path(), queryString);
    }

}
