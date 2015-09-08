package common.pages;

import play.mvc.Call;

public interface ReverseRouter {

    Call home(final String languageTag);

    Call category(final String languageTag, final String slug, final int page);

    Call productVariantToCartForm(final String languageTag);

    Call changeLineItemQuantityForm(final String languageTag);

    Call product(final String locale, final String productSlug, final String sku);

    Call cart(final String languageTag);

    Call checkoutShipping(final String languageTag);
}

