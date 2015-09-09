package productcatalog.pages;

import common.pages.SelectableLinkData;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.PagedResult;
import org.junit.Test;
import play.api.http.MediaRange;
import play.api.mvc.Request;
import play.api.mvc.RequestHeader;
import play.i18n.Lang;
import play.mvc.Http;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginationDataFactoryTest {
    private static final String URL_PATH = "www.url.dom/path/to/";
    private static final int PAGE_SIZE = 9;

    @Test
    public void calculatesProductCountAndTotal() throws Exception {
        final int page = 2;
        final int totalPages = 10;
        final int displayedPages = 5;
        final PaginationData paginationData = createPaginationData(page, displayedPages, pagedResult(page, totalPages));
        assertThat(paginationData.getProductsCount()).isEqualTo(page * PAGE_SIZE);
        assertThat(paginationData.getTotalProducts()).isEqualTo(totalPages * PAGE_SIZE);
    }

    @Test
    public void calculatesPagination() throws Exception {
        final int page = 3;
        final int totalPages = 5;
        final int displayedPages = 5;
        final PaginationData paginationData = createPaginationData(page, displayedPages, pagedResult(page, totalPages));
        assertThat(paginationData.getPrevPage().getText()).isEqualTo("2");
        assertThat(paginationData.getNextPage().getText()).isEqualTo("4");
        assertThat(paginationData.getFirstPage()).isNull();
        assertThat(paginationData.getLastPage()).isNull();
        assertThat(paginationData.getPages())
                .extracting(SelectableLinkData::getText)
                .containsExactly("1", "2", "3", "4", "5");
        assertThat(paginationData.getPages())
                .extracting(SelectableLinkData::isSelected)
                .containsExactly(false, false, true, false, false);
    }

    @Test
    public void calculatesPaginationForFirstPage() throws Exception {
        final int page = 1;
        final int totalPages = 10;
        final int displayedPages = 5;
        final PaginationData paginationData = createPaginationData(page, displayedPages, pagedResult(page, totalPages));
        assertThat(paginationData.getPrevPage()).isNull();
        assertThat(paginationData.getNextPage().getText()).isEqualTo("2");
        assertThat(paginationData.getFirstPage()).isNull();
        assertThat(paginationData.getLastPage().getText()).isEqualTo("10");
    }

    @Test
    public void calculatesPaginationForLastPage() throws Exception {
        final int page = 10;
        final int totalPages = 10;
        final int displayedPages = 5;
        final PaginationData paginationData = createPaginationData(page, displayedPages, pagedResult(page, totalPages));
        assertThat(paginationData.getPrevPage().getText()).isEqualTo("9");
        assertThat(paginationData.getNextPage()).isNull();
        assertThat(paginationData.getFirstPage().getText()).isEqualTo("1");
        assertThat(paginationData.getLastPage()).isNull();
    }

    @Test
    public void calculatesPaginationForAllFirstPages() throws Exception {
        final int totalPages = 10;
        final int displayedPages = 5;
        IntStream.rangeClosed(1, 3)
                .forEach(page -> {
                    final PaginationData paginationData = createPaginationData(page, displayedPages, pagedResult(page, totalPages));
                    assertThat(paginationData.getPages())
                            .extracting(SelectableLinkData::getText)
                            .containsExactly("1", "2", "3", "4");
                });
    }

    @Test
    public void calculatesPaginationForAllLastPages() throws Exception {
        final int displayedPages = 5;
        final int totalPages = 10;
        IntStream.rangeClosed(8, 10)
                .forEach(page -> {
                    final PaginationData paginationData = createPaginationData(page, displayedPages, pagedResult(page, totalPages));
                    assertThat(paginationData.getPages())
                            .extracting(SelectableLinkData::getText)
                            .containsExactly("7", "8", "9", "10");
                });
    }

    private PaginationData createPaginationData(final int currentPage, final int displayedPages, final PagedResult<ProductProjection> searchResult) {
        return new PaginationDataFactory(request(currentPage), searchResult, currentPage, PAGE_SIZE, displayedPages).create();
    }

    @SuppressWarnings("unchecked")
    private PagedResult<ProductProjection> pagedResult(final int page, final int totalPages) {
        final int offset = (page - 1) * PAGE_SIZE;
        final int totalProducts = totalPages * PAGE_SIZE;
        final List<ProductProjection> products = Collections.nCopies(PAGE_SIZE, null);
        return new PagedResult(offset, totalProducts, products) {};
    }

    public Map<String, String[]> buildQueryString(final int currentPage) {
        final LinkedHashMap<String, String[]> queryString = new LinkedHashMap<>();
        queryString.put("foo", new String[] {"bar"});
        queryString.put("page", new String[] {String.valueOf(currentPage)});
        return queryString;
    }

    public Http.Request request(final int currentPage) {
        return new Http.Request() {
            @Override
            public Http.RequestBody body() {
                return null;
            }

            @Override
            public String username() {
                return null;
            }

            @Override
            public void setUsername(final String username) {

            }

            @Override
            public Http.Request withUsername(final String username) {
                return null;
            }

            @Override
            public Request<Http.RequestBody> _underlyingRequest() {
                return null;
            }

            @Override
            public String uri() {
                return null;
            }

            @Override
            public String method() {
                return null;
            }

            @Override
            public String version() {
                return null;
            }

            @Override
            public String remoteAddress() {
                return null;
            }

            @Override
            public boolean secure() {
                return false;
            }

            @Override
            public String host() {
                return null;
            }

            @Override
            public String path() {
                return URL_PATH;
            }

            @Override
            public List<Lang> acceptLanguages() {
                return null;
            }

            @Override
            public List<MediaRange> acceptedTypes() {
                return null;
            }

            @Override
            public boolean accepts(final String mimeType) {
                return false;
            }

            @Override
            public Map<String, String[]> queryString() {
                return buildQueryString(currentPage);
            }

            @Override
            public String getQueryString(final String key) {
                return null;
            }

            @Override
            public Http.Cookies cookies() {
                return null;
            }

            @Override
            public Http.Cookie cookie(final String name) {
                return null;
            }

            @Override
            public Map<String, String[]> headers() {
                return null;
            }

            @Override
            public String getHeader(final String headerName) {
                return null;
            }

            @Override
            public boolean hasHeader(final String headerName) {
                return false;
            }

            @Override
            public RequestHeader _underlyingHeader() {
                return null;
            }
        };
    }
}
