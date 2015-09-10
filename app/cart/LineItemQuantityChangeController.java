package cart;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.ChangeLineItemQuantity;
import io.sphere.sdk.carts.commands.updateactions.RemoveLineItem;
import io.sphere.sdk.models.Base;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.Result;

import javax.inject.Inject;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

/**
 * Changes the quantity of a line item in a cart or even removes it. In success case returns to the cart detail page.
 */
public class LineItemQuantityChangeController extends CartController {
    private static final String DELETE = "delete";
    private static final String EDIT = "edit";

    @Inject
    public LineItemQuantityChangeController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> process(final String languageTag) {
        final Form<LineItemQuantityFormData> filledForm = bindForm();
        if (filledForm.hasErrors()) {
            return F.Promise.pure(redirect(reverseRouter().cart(languageTag)));
        } else {
            final UserContext userContext = userContext(languageTag);
            final F.Promise<Cart> cartPromise = getOrCreateCart(userContext, session());
            final LineItemQuantityFormData formData = filledForm.get();
            final String subject = formData.getSubject();
            if (EDIT.equals(subject)) {
                return cartPromise.flatMap(cart -> changeQuantity(formData, cart, languageTag));
            } else if (DELETE.equals(subject)) {
                return cartPromise.flatMap(cart -> deleteFromCart(formData, cart, languageTag));
            } else {
                Logger.error("Subject '{}' does not match the requirements.", subject);
                return F.Promise.pure(redirect(reverseRouter().cart(languageTag)));
            }
        }
    }

    private F.Promise<Result> deleteFromCart(final LineItemQuantityFormData formData, final Cart cart, final String languageTag) {
        final String lineItemId = formData.getLineItemId();
        return sphere().execute(CartUpdateCommand.of(cart, RemoveLineItem.of(lineItemId)))
                .map(updatedCart -> {
                    CartSessionUtils.overwriteCartSessionData(cart, session());
                    return redirect(reverseRouter().cart(languageTag));
                });
    }

    private F.Promise<Result> changeQuantity(final LineItemQuantityFormData formData, final Cart cart, final String languageTag) {
        final String lineItemId = formData.getLineItemId();
        final Long newQuantity = firstNonNull(formData.getQuantity(), 0L);
        return sphere().execute(CartUpdateCommand.of(cart, ChangeLineItemQuantity.of(lineItemId, newQuantity)))
                .map(updatedCart -> {
                    CartSessionUtils.overwriteCartSessionData(cart, session());
                    return redirect(reverseRouter().cart(languageTag));
                });
    }

    private Form<LineItemQuantityFormData> bindForm() {
        return Form.form(LineItemQuantityFormData.class).bindFromRequest();
    }

    public static class LineItemQuantityFormData extends Base {
        @Constraints.Required
        @Constraints.Pattern(DELETE + "|" + EDIT)
        private String subject;
        @Constraints.Required
        private String lineItemId;
        @Constraints.Min(0)
        private Long quantity;

        public LineItemQuantityFormData() {
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(final String subject) {
            this.subject = subject;
        }

        public String getLineItemId() {
            return lineItemId;
        }

        public void setLineItemId(final String lineItemId) {
            this.lineItemId = lineItemId;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(final Long quantity) {
            this.quantity = quantity;
        }
    }
}
