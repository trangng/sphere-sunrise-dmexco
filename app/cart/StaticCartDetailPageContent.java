package cart;

import io.sphere.sdk.models.Base;

public class StaticCartDetailPageContent extends Base {
    public String getYourBag() {
        return "Your bag:";
    }

    public String getCheckoutButtonLabel() {
        return "Checkout now";
    }

    public String getProductDescriptionTitle() {
        return "Product Description";
    }

    public String getQuantityTitle() {
        return "Quantity";
    }

    public String getPriceTitle() {
        return "Price";
    }

    public String getTotalPriceTitle() {
        return "Total Price";
    }

    public String getAvailableLabel() {
        return "Available";
    }

    public String getEditLabel() {
        return "Edit";
    }

    public String getDeleteLabel() {
        return "Delete";
    }

    public String getColorTitle() {
        return "Color: ";
    }

    public String getSizeTitle() {
        return "Size: ";
    }
}
