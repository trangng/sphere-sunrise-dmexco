package productcatalog.pages;

import common.pages.ReverseRouter;
import play.mvc.Call;

public class DefaultTestReverseRouter implements ReverseRouter {
    @Override
    public Call home(final String language) {
        return null;
    }

    @Override
    public Call category(final String locale, final String slug, final int page) {
        return null;
    }

    @Override
    public Call product(final String locale, final String productSlug, final String sku) {
        return new Call() {
            @Override
            public String url() {
                return productSlug + "-url-" + sku;
            }

            @Override
            public String method() {
                return null;
            }

            @Override
            public String fragment() {
                return null;
            }
        };
    }
}
