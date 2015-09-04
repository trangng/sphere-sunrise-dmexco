package cart;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.pages.SunrisePageData;
import io.sphere.sdk.carts.Cart;
import play.libs.F;
import play.mvc.Result;

import javax.inject.Inject;

public class CartDetailPageController extends CartController {

    @Inject
    public CartDetailPageController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> show(final String language) {
        final UserContext userContext = userContext(language);
        final F.Promise<Cart> cartPromise = getOrCreateCart(userContext, session());
        return cartPromise.map(cart -> {
            final CartDetailPageContent content = new CartDetailPageContent();
            final SunrisePageData pageData = pageData(userContext, content);
            return ok(templateService().renderToHtml("cart", pageData));
        });
    }
}
