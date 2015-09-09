package productcatalog.pages;

import common.pages.ReverseRouter;
import play.mvc.Call;

public class DefaultTestReverseRouter implements ReverseRouter {
    @Override
    public Call home(final String languageTag) {
        return null;
    }

    @Override
    public Call category(final String languageTag, final String slug, final int page) {
        return null;
    }

    @Override
    public Call productVariantToCartForm(final String languageTag) {
        return null;
    }

    @Override
    public Call product(final String languageTag, final String productSlug, final String sku) {
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

    @Override
    public Call changeLineItemQuantityForm(final String languageTag) {
        return null;
    }

    @Override
    public Call cart(final String languageTag) {
        return null;
    }

    @Override
    public Call search(final String languageTag, final String searchTerm, final int page) {
        return null;
    }
}
