package cart;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;

public class CheckoutShippingFormData extends Base {

    //alpha-2
    private String country;
    private String title;


    public CheckoutShippingFormData() {
    }

    public CheckoutShippingFormData(final Cart cart) {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
