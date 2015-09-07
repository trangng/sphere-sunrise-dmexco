package cart;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.queries.CartByIdGet;
import play.libs.F;
import play.mvc.Http;

import java.util.Optional;

public abstract class CartController extends SunriseController {
    public CartController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    protected F.Promise<Cart> getOrCreateCart(final UserContext userContext, final Http.Session session) {
        return Optional.ofNullable(session(CartSessionKeys.CART_ID))
                .map(cartId -> sphere().execute(CartByIdGet.of(cartId)))
                .orElseGet(() -> sphere().execute(CartCreateCommand.of(CartDraft.of(userContext.currency()).withCountry(userContext.country())))
                        .map(cart -> {
                            session.put(CartSessionKeys.CART_ID, cart.getId());
                            return cart;
                        }));
    }
}
