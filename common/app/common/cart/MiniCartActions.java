package common.cart;

import java.util.Optional;

import static play.mvc.Controller.session;

public class MiniCartActions {


    public static void decreaseCartItemCount(final Long itemCount) {
        increaseCartItemCount(-itemCount);
    }

    public static void increaseCartItemCount(final Long itemCount) {
        setCartItemCount(getCartItemCount() + itemCount);
    }

    public static void setCartItemCount(final Long itemCount) {
        session().put(CartSessionKeys.CART_ITEM_COUNT, String.valueOf(itemCount));
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
