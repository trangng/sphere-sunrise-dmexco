package common.utils;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static common.utils.UrlUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UrlUtilsTest {
    private static final String URL_PATH = "www.url.dom/path/to/";
    private static final String QUERY_STRING = "foo=one&foo=two&foo=three";

    @Test
    public void buildsQueryStringWithNoParameters() throws Exception {
        final Map<String, String[]> queryString = new LinkedHashMap<>();
        assertThat(buildQueryString(queryString)).isEmpty();
    }

    @Test
    public void buildsQueryStringWithSingleParameter() throws Exception {
        final Map<String, String[]> queryString = new LinkedHashMap<>();
        queryString.put("foo", new String[]{"one", "two", "three"});
        assertThat(buildQueryString(queryString)).isEqualTo("foo=one&foo=two&foo=three");
    }

    @Test
    public void buildsQueryStringWithMultipleParameters() throws Exception {
        final Map<String, String[]> queryString = new LinkedHashMap<>();
        queryString.put("foo", new String[]{"one", "two", "three"});
        queryString.put("bar", new String[]{"four", "five"});
        assertThat(buildQueryString(queryString)).isEqualTo("foo=one&foo=two&foo=three&bar=four&bar=five");
    }

    @Test
    public void buildsUrlWithQueryString() throws Exception {
        final String url = buildUrl(URL_PATH, QUERY_STRING);
        assertThat(url).isEqualTo(URL_PATH + "?" + QUERY_STRING);
    }

    @Test
    public void buildsUrlWithoutQueryString() throws Exception {
        final String url = buildUrl(URL_PATH, "");
        assertThat(url).isEqualTo(URL_PATH);
    }

    @Test
    public void buildsUrlAndQueryString() throws Exception {
        final Map<String, String[]> queryString = new LinkedHashMap<>();
        queryString.put("foo", new String[]{"one", "two", "three"});
        final String url = buildUrl(URL_PATH, queryString);
        assertThat(url).isEqualTo(URL_PATH + "?foo=one&foo=two&foo=three");
    }
}
