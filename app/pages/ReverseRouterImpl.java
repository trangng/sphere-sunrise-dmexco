package pages;

import cart.routes;
import common.pages.ReverseRouter;
import io.sphere.sdk.models.Base;
import play.mvc.Call;

public class ReverseRouterImpl extends Base implements ReverseRouter {

    @Override
    public Call home(final String languageTag) {
        return controllers.routes.HomeController.show(languageTag);
    }

    @Override
    public Call category(final String languageTag, final String slug, final int page) {
        return productcatalog.controllers.routes.ProductOverviewPageController.show(languageTag, slug, page);
    }

    @Override
    public Call productVariantToCartForm(final String languageTag) {
        return cart.routes.LineItemAddController.process(languageTag);
    }

    @Override
    public Call product(final String languageTag, final String productSlug, final String sku) {
        return productcatalog.controllers.routes.ProductDetailPageController.show(languageTag, productSlug, sku);
    }

    @Override
    public Call cart(final String languageTag) {
        return cart.routes.CartDetailPageController.show(languageTag);
    }

    @Override
    public Call changeLineItemQuantityForm(final String languageTag) {
        return routes.LineItemQuantityChangeController.process(languageTag);
    }

    @Override
    public Call checkoutShipping(final String languageTag) {
        return routes.CheckoutShippingController.show(languageTag);
    }
}
