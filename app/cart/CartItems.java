package cart;

import io.sphere.sdk.models.Base;

import java.util.List;

public class CartItems extends Base {
    private List<CartItem> list;

    public CartItems() {
    }

    public List<CartItem> getList() {
        return list;
    }

    public void setList(final List<CartItem> list) {
        this.list = list;
    }
}
