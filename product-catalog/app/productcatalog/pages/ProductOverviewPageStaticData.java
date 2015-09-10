package productcatalog.pages;

import io.sphere.sdk.models.Base;
import play.i18n.Messages;

public class ProductOverviewPageStaticData extends Base {
    private final Messages messages;

    public ProductOverviewPageStaticData(final Messages messages) {
        this.messages = messages;
    }

    public String getViewDetailsText() {
        return messages.at("pdp.viewDetailsText");
    }

    public String getColorsText() {
        return messages.at("pdp.colorsText");
    }

    public String getSizesText() {
        return messages.at("pdp.sizesText");
    }

    public String getSizeGuideText() {
        return messages.at("pdp.sizeGuideText");
    }

    public String getAddToBagText() {
        return messages.at("pdp.addToBagText");
    }

    public String getAddToWishlistText() {
        return messages.at("pdp.addToWishlistText");
    }

    public String getSaleText() {
        return messages.at("pdp.saleText");
    }

    public String getNewText() {
        return messages.at("pdp.newText");
    }

    public String getQuickViewText() {
        return messages.at("pdp.quickViewText");
    }

    public String getWishlistText() {
        return messages.at("pdp.wishlistText");
    }

    public String getMoreColorsText() {
        return messages.at("pdp.moreColorsText");
    }

    public String getReviewsText() {
        return messages.at("pdp.reviewsText");
    }

    public String getProductCountSeparatorText() {
        return messages.at("pop.productCountSeparator");
    }

    public String getDisplaySelectorText() {
        return messages.at("pop.displaySelectorText");
    }
}
