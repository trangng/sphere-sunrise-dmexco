package cart;

import common.contexts.UserContext;
import common.utils.PriceFormatter;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.MoneyImpl;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CartItems extends Base {
    private List<CartItem> list;
    private String subtotal;
    private String orderDiscount;
    private String delivery;
    private String tax;
    private String orderTotal;

    public CartItems() {
    }

    public static CartItems of(final Cart cart, final UserContext userContext) {
        final CartItems cartItems = new CartItems();
        final List<CartItem> cartItemList = cart.getLineItems()
                .stream()
                .map(lineItem -> CartItem.of(lineItem, userContext))
                .collect(toList());
        cartItems.setList(cartItemList);
        final PriceFormatter priceFormatter = PriceFormatter.of(userContext.locale());
        final String subtotal = priceFormatter.format(cart.getTotalPrice());
        cartItems.setSubtotal(subtotal);
        final String zeroMoneyAmount = priceFormatter.format(MoneyImpl.ofCents(0, cart.getTotalPrice().getCurrency()));
        cartItems.setOrderDiscount(zeroMoneyAmount);
        final String delivery = Optional.ofNullable(cart.getShippingInfo())
                .map(cartShippingInfo -> priceFormatter.format(cartShippingInfo.getPrice()))
                .orElse(zeroMoneyAmount);
        cartItems.setDelivery(delivery);
        final String tax = Optional.ofNullable(cart.getTaxedPrice())
                .map(taxedPrice -> taxedPrice.getTotalGross().subtract(taxedPrice.getTotalNet()))
                .map(amount -> priceFormatter.format(amount))
                .orElse(zeroMoneyAmount);
        cartItems.setTax(tax);
        final String orderTotal = Optional.ofNullable(cart.getTaxedPrice())
                .map(taxedPrice -> priceFormatter.format(taxedPrice.getTotalNet()))
                .orElse(subtotal);
        cartItems.setOrderTotal(orderTotal);
        return cartItems;
    }

    public List<CartItem> getList() {
        return list;
    }

    public void setList(final List<CartItem> list) {
        this.list = list;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(final String subtotal) {
        this.subtotal = subtotal;
    }

    public String getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(final String orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(final String delivery) {
        this.delivery = delivery;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(final String tax) {
        this.tax = tax;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(final String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
