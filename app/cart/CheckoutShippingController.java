package cart;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.pages.SunrisePageData;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.models.Address;
import play.Logger;
import play.data.Form;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Result;

import javax.inject.Inject;

public class CheckoutShippingController extends CartController {

    @Inject
    public CheckoutShippingController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> show(final String languageTag) {
        final UserContext userContext = userContext(languageTag);
        final F.Promise<Cart> cartPromise = getOrCreateCart(userContext, session());
        return cartPromise.map(cart -> {
            final Messages messages = messages(userContext);
            final CheckoutShippingContent content = new CheckoutShippingContent(cart, messages, reverseRouter(), userContext);
            final SunrisePageData pageData = pageData(userContext, content);
            return ok(templateService().renderToHtml("checkout-shipping", pageData));
        });
    }

    public F.Promise<Result> process(final String languageTag) {
        final Form<CheckoutShippingFormData> filledForm = Form.form(CheckoutShippingFormData.class).bindFromRequest();
        if (filledForm.hasErrors()) {
            Logger.debug("form has errors {}", filledForm.errorsAsJson());
            return F.Promise.pure(redirect(reverseRouter().processCheckoutShipping(languageTag)));
        } else {
            final UserContext userContext = userContext(languageTag);
            final F.Promise<Cart> cartPromise = getOrCreateCart(userContext, session());
            return cartPromise.flatMap(cart -> {
                final CheckoutShippingFormData data = filledForm.get();
                final Address shippingAddress = data.getShippingAddress();
                return sphere().execute(CartUpdateCommand.of(cart, SetShippingAddress.of(shippingAddress)));
            })
            .map(updatedCart -> redirect(reverseRouter().processCheckoutShipping(languageTag)));
        }
    }

}
