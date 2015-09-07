package cart;

import io.sphere.sdk.models.Base;

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
