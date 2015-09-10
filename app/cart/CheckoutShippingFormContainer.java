package cart;

import io.sphere.sdk.models.Base;

public class CheckoutShippingFormContainer extends Base {
    private CheckoutShippingFormData form;
    private String formAction;

    public CheckoutShippingFormContainer() {
    }

    public CheckoutShippingFormData getForm() {
        return form;
    }

    public void setForm(final CheckoutShippingFormData form) {
        this.form = form;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(final String formAction) {
        this.formAction = formAction;
    }
}
