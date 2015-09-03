package common.pages;

import play.mvc.Call;

public interface ReverseRouter {

    Call category(final String language, final String slug, final int page);

    Call productVariantToCartForm(final String language);

    Call product(final String locale, final String productSlug, final String sku);
}

