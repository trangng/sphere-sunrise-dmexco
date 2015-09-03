package common.pages;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.Base;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MiniCartData extends Base {

    final String text;
    final String url;
    final long numItems;

    public MiniCartData(final Cart cart) {
        this.text = "";
        this.url = getUrlFromCart(cart);
        this.numItems = getNumItemsFromCart(cart);
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public long getNumItems() {
        return numItems;
    }

    private String getUrlFromCart(final Cart cart) {
        return "";
    }

    private long getNumItemsFromCart(final Cart cart) {
        return Stream.concat(
                cart.getLineItems().stream().map(LineItem::getQuantity),
                cart.getCustomLineItems().stream().map(CustomLineItem::getQuantity))
                .mapToLong(i -> i).sum();
    }
}
