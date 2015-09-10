package cart;

import common.contexts.UserContext;
import common.pages.PageContent;
import common.pages.ReverseRouter;
import io.sphere.sdk.carts.Cart;
import play.i18n.Messages;

public class CheckoutShippingContent extends PageContent {
    private final StaticCartDetailPageContent staticData = new StaticCartDetailPageContent();
    private final Messages messages;
    private final StepWidgetData stepWidget = new StepWidgetData();
    private final CartItems cartItems;
    private final CheckoutShippingFormContainer shippingFormContainer;

    public CheckoutShippingContent(final Cart cart, final Messages messages, final ReverseRouter reverseRouter, final UserContext userContext) {
        final String shippingStepUrl = reverseRouter.checkoutShipping(userContext.locale().toLanguageTag()).url();
        stepWidget.setShippingStepUrl(shippingStepUrl);
        stepWidget.setShippingStepActive(true);
        this.messages = messages;
        cartItems = CartItems.of(cart, userContext, messages, reverseRouter);
        shippingFormContainer = new CheckoutShippingFormContainer(userContext);
        shippingFormContainer.setFormAction(reverseRouter.processCheckoutShipping(userContext.locale().toLanguageTag()).url());
        shippingFormContainer.setForm(new CheckoutShippingFormData(cart));
    }

    @Override
    public String additionalTitle() {
        return "Checkout Shipping Page";
    }

    public Messages getMessages() {
        return messages;
    }

    public StepWidgetData getStepWidget() {
        return stepWidget;
    }

    public CartItems getCartItems() {
        return cartItems;
    }

    public StaticCartDetailPageContent getStatic() {
        return staticData;
    }

    public CheckoutShippingFormContainer getShippingFormContainer() {
        return shippingFormContainer;
    }
}
