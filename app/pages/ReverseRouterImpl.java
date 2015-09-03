package pages;

import common.pages.ReverseRouter;
import io.sphere.sdk.models.Base;
import play.mvc.Call;
import productcatalog.controllers.routes;

public class ReverseRouterImpl extends Base implements ReverseRouter {

    @Override
    public Call category(final String locale, final String slug, final int page) {
        return routes.ProductOverviewPageController.show(locale, slug, page);
    }

    @Override
    public Call product(final String locale, final String productSlug, final String sku) {
        return routes.ProductDetailPageController.show(locale, productSlug, sku);
    }

    @Override
    public Call productVariantToCartForm(final String language) {
        return cart.routes.LineItemAddController.process(language);
    }

    @Override
    public Call product(final String locale, final String productSlug, final String sku) {
        return routes.ProductDetailPageController.show(locale, productSlug, sku);
    }
}
