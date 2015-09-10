package cart;

import common.contexts.UserContext;
import common.pages.PageContent;
import common.pages.ReverseRouter;
import io.sphere.sdk.carts.Cart;
import play.i18n.Messages;

public class CartDetailPageContent extends PageContent {
    private final CartItems cartItems;
    private final StaticCartDetailPageContent staticData = new StaticCartDetailPageContent();
    private final String editQuantityFormUrl;
    private final String checkoutShippingUrl;
    private final Messages messages;


    public CartDetailPageContent(final Cart cart, final UserContext userContext, final Messages messages, final ReverseRouter reverseRouter) {
        this.messages = messages;
        cartItems = CartItems.of(cart, userContext, messages, reverseRouter);
        final String languageTag = userContext.locale().toLanguageTag();
        editQuantityFormUrl = reverseRouter.changeLineItemQuantityForm(languageTag).url();
        checkoutShippingUrl = reverseRouter.checkoutShipping(languageTag).url();
    }

    @Override
    public String additionalTitle() {
        return "Cart Page";
    }

    public CartItems getCartItems() {
        return cartItems;
    }

    public StaticCartDetailPageContent getStatic() {
        return staticData;
    }

    public String getEditQuantityFormUrl() {
        return editQuantityFormUrl;
    }

    public String getCheckoutShippingUrl() {
        return checkoutShippingUrl;
    }

    public Messages getMessages() {
        return messages;
    }
}
