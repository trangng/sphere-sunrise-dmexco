package cart;

import common.contexts.UserContext;
import common.pages.PageContent;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import play.i18n.Messages;

public class CartDetailPageContent extends PageContent {
    private final CartItems cartItems;
    private final StaticCartDetailPageContent staticData = new StaticCartDetailPageContent();
    private final String itemsTotal;


    public CartDetailPageContent(final Cart cart, final UserContext userContext, final Messages messages) {
        final long totalItems = cart.getLineItems().stream().mapToLong(LineItem::getQuantity).sum();
        itemsTotal = messages.at("cdp.totalItems", totalItems);
        cartItems = CartItems.of(cart, userContext);
    }

    @Override
    public String additionalTitle() {
        return "Cart Page";
    }

    public CartItems getCartItems() {
        return cartItems;
    }

    public String getItemsTotal() {
        return itemsTotal;
    }

    public StaticCartDetailPageContent getStatic() {
        return staticData;
    }
}
