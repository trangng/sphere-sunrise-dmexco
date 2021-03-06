package cart;

import common.contexts.UserContext;
import common.pages.PageContent;
import common.pages.ReverseRouter;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import play.i18n.Messages;

public class CartDetailPageContent extends PageContent {
    private final CartItems cartItems;
    private final StaticCartDetailPageContent staticData = new StaticCartDetailPageContent();
    private final String itemsTotal;
    private final String editQuantityFormUrl;


    public CartDetailPageContent(final Cart cart, final UserContext userContext, final Messages messages, final ReverseRouter reverseRouter) {
        final long totalItems = cart.getLineItems().stream().mapToLong(LineItem::getQuantity).sum();
        itemsTotal = messages.at("cdp.totalItems", totalItems);
        cartItems = CartItems.of(cart, userContext);
        editQuantityFormUrl = reverseRouter.changeLineItemQuantityForm(userContext.locale().toLanguageTag()).url();
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


    public String getEditQuantityFormUrl() {
        return editQuantityFormUrl;
    }
}
