package pages;

import common.pages.ReverseRouter;
import io.sphere.sdk.models.Base;
import play.mvc.Call;

public class ReverseRouterImpl extends Base implements ReverseRouter {

    @Override
    public Call home(final String language) {
        return controllers.routes.HomeController.show(language);
    }

    @Override
    public Call category(final String locale, final String slug, final int page) {
        return productcatalog.controllers.routes.ProductOverviewPageController.show(locale, slug, page);
    }

    @Override
    public Call productVariantToCartForm(final String language) {
        return cart.routes.LineItemAddController.process(language);
    }

    @Override
    public Call product(final String locale, final String productSlug, final String sku) {
        return productcatalog.controllers.routes.ProductDetailPageController.show(locale, productSlug, sku);
    }
}
