package common.cart;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;

import java.util.Optional;

import static play.mvc.Controller.session;

public class MiniCart {

    public static Cart updateCartItemCount(final Cart cart) {
        final Long itemCount = cart.getLineItems().stream().mapToLong(LineItem::getQuantity).sum();
        session().put(CartSessionKeys.CART_ITEM_COUNT, String.valueOf(itemCount));
        return cart;
    }

    public static Long getCartItemCount() {
        return readLong(CartSessionKeys.CART_ITEM_COUNT).orElse(0L);
    }

    private static Optional<Long> readLong(final String key) {
        try {
            return Optional.of(Long.parseLong(session(key)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
