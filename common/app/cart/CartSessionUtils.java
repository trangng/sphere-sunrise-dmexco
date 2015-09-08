package cart;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import play.Logger;
import play.mvc.Http.Session;

import java.util.Optional;

public class CartSessionUtils {

    public static long getCartItemCount(final Session session) {
        return readLong(CartSessionKeys.CART_ITEM_COUNT, session).orElse(0L);
    }

    private static Optional<Long> readLong(final String key, final Session session) {
        try {
            return Optional.of(Long.parseLong(session.get(key)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void overwriteCartSessionData(final Cart cart, final Session session) {
        final long itemCount = cart.getLineItems().stream().mapToLong(LineItem::getQuantity).sum();
        session.put(CartSessionKeys.CART_ID, cart.getId());
        session.put(CartSessionKeys.CART_ITEM_COUNT, String.valueOf(itemCount));
        Logger.debug(session.toString());
    }
}