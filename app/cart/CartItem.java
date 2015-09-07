package cart;

import common.contexts.UserContext;
import common.utils.PriceFormatter;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributes.AttributeAccess;

import javax.money.MonetaryAmount;
import java.util.Optional;

public class CartItem extends Base {
    private String nameHeadline;
    private String nameSubline;
    private String sku;
    private String color;
    private String size;
    private String priceOld;
    private String price;
    private String totalPrice;
    private String imageUrl;
    private Long quantity;

    public CartItem() {
    }

    public static CartItem of(final LineItem lineItem, final UserContext userContext) {
        final CartItem cartItem = new CartItem();
        cartItem.setNameHeadline(lineItem.getName().find(userContext.locales()).orElse(""));
        cartItem.setNameSubline("");
        final ProductVariant productVariant = lineItem.getVariant();
        cartItem.setSku(productVariant.getSku());
        cartItem.setColor(productVariant.getAttribute("color").getValue(AttributeAccess.ofLocalizedEnumValue()).getLabel().get(userContext.locales()));
        cartItem.setSize(productVariant.getAttribute("size").getValue(AttributeAccess.ofString()));
        final PriceFormatter priceFormatter = PriceFormatter.of(userContext.locale());
        cartItem.setPrice(priceFormatter.format(lineItem.getPrice().getValue()));
        cartItem.setQuantity(lineItem.getQuantity());
        final MonetaryAmount monetaryAmount = Optional.ofNullable(lineItem.getPrice().getDiscounted())
                .map(DiscountedPrice::getValue)
                .orElseGet(() -> lineItem.getPrice().getValue());
        cartItem.setTotalPrice(priceFormatter.format(monetaryAmount.multiply(lineItem.getQuantity())));
        final String imageUrl = productVariant.getImages().stream().findFirst().map(i -> i.getUrl()).orElse("");
        cartItem.setImageUrl(imageUrl);
        return cartItem;
    }

    public String getNameHeadline() {
        return nameHeadline;
    }

    public void setNameHeadline(final String nameHeadline) {
        this.nameHeadline = nameHeadline;
    }

    public String getNameSubline() {
        return nameSubline;
    }

    public void setNameSubline(final String nameSubline) {
        this.nameSubline = nameSubline;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(final String sku) {
        this.sku = sku;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(final String priceOld) {
        this.priceOld = priceOld;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
