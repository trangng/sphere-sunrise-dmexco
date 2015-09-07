package common.cart;

import play.mvc.Http.Session;

import java.util.Optional;

public class MiniCartActions {

    public static void decreaseCartItemCount(final Long itemCount, final Session session) {
        increaseCartItemCount(-itemCount, session);
    }

    public static void increaseCartItemCount(final Long itemCount, final Session session) {
        setCartItemCount(getCartItemCount(session) + itemCount, session);
    }

    public static void setCartItemCount(final Long itemCount, final Session session) {
        session.put(CartSessionKeys.CART_ITEM_COUNT, String.valueOf(itemCount));
    }

    public static Long getCartItemCount(final Session session) {
        return readLong(CartSessionKeys.CART_ITEM_COUNT, session).orElse(0L);
    }

    private static Optional<Long> readLong(final String key, final Session session) {
        try {
            return Optional.of(Long.parseLong(session.get(key)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}