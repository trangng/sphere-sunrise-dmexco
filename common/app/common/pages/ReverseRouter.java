package common.pages;

import play.mvc.Call;

public interface ReverseRouter {

    Call category(final String locale, final String slug, final int page);

    Call product(final String locale, final String productSlug, final String sku);
}
