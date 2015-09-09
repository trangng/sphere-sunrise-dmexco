package cart;

import io.sphere.sdk.models.Base;

public class StepWidgetData extends Base {
    private String shippingStepUrl;
    private String paymentStepUrl;
    private String confirmationStepUrl;
    private boolean shippingStepActive;


    public StepWidgetData() {
    }

    public String getShippingStepUrl() {
        return shippingStepUrl;
    }

    public void setShippingStepUrl(final String shippingStepUrl) {
        this.shippingStepUrl = shippingStepUrl;
    }

    public String getPaymentStepUrl() {
        return paymentStepUrl;
    }

    public void setPaymentStepUrl(final String paymentStepUrl) {
        this.paymentStepUrl = paymentStepUrl;
    }

    public String getConfirmationStepUrl() {
        return confirmationStepUrl;
    }

    public void setConfirmationStepUrl(final String confirmationStepUrl) {
        this.confirmationStepUrl = confirmationStepUrl;
    }

    public boolean isShippingStepActive() {
        return shippingStepActive;
    }

    public void setShippingStepActive(final boolean shippingStepActive) {
        this.shippingStepActive = shippingStepActive;
    }
}
