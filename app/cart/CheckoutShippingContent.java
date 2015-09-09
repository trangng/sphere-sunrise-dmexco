package cart;

import common.contexts.UserContext;
import common.pages.PageContent;
import common.pages.ReverseRouter;
import play.i18n.Messages;

public class CheckoutShippingContent extends PageContent {

    private final Messages messages;
    private final StepWidgetData stepWidget = new StepWidgetData();

    public CheckoutShippingContent(final Messages messages, final ReverseRouter reverseRouter, final UserContext userContext) {
        final String shippingStepUrl = reverseRouter.checkoutShipping(userContext.locale().toLanguageTag()).url();
        stepWidget.setShippingStepUrl(shippingStepUrl);
        stepWidget.setShippingStepActive(true);
        this.messages = messages;
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
}
