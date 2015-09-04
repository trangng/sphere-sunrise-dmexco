package cart;

import common.contexts.UserContext;
import common.pages.PageContent;
import common.pages.ReverseRouter;
import common.utils.PriceFormatter;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.attributes.AttributeAccess;
import play.i18n.Messages;
import productcatalog.pages.ProductDataFactory;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CartDetailPageContent extends PageContent {
    private final CartItems cartItems = new CartItems();
    private final String itemsTotal;


    public CartDetailPageContent(final Cart cart, final UserContext userContext, final ReverseRouter reverseRouter, final Messages messages) {
        final long totalItems = cart.getLineItems().stream().mapToLong(lineItem -> lineItem.getQuantity()).sum();
        itemsTotal = messages.get("cdp.totalItems", totalItems);
        final ProductDataFactory productDataFactory = ProductDataFactory.of(userContext, reverseRouter);
        final List<CartItem> cartItemList = cart.getLineItems()
                .stream()
                .map(lineItem -> {
                    final CartItem cartItem = new CartItem();
                    cartItem.setNameHeadline(lineItem.getName().find(userContext.locales()).orElse(""));
                    cartItem.setNameSubline("");
                    final ProductVariant productVariant = lineItem.getVariant();
                    cartItem.setSku(productVariant.getSku());
                    cartItem.setColor(productVariant.getAttribute("color").getValue(AttributeAccess.ofLocalizedEnumValue()).getLabel().get(userContext.locales()));
                    cartItem.setSize(productVariant.getAttribute("size").getValue(AttributeAccess.ofString()));
                    cartItem.setPrice(productDataFactory.getPrice(productVariant));
                    cartItem.setPriceOld(productDataFactory.getOldPrice(productVariant));
                    cartItem.setQuantity(lineItem.getQuantity());
                    final MonetaryAmount monetaryAmount = Optional.ofNullable(lineItem.getPrice().getDiscounted())
                            .map(DiscountedPrice::getValue)
                            .orElseGet(() -> lineItem.getPrice().getValue());
                    cartItem.setTotalPrice(PriceFormatter.of(userContext.locale()).format(monetaryAmount));

                    return cartItem;
                })
                .collect(toList());
        cartItems.setList(cartItemList);
    }

    @Override
    public String additionalTitle() {
        return "Cart Page";
    }

    public CartItems getCartItems() {
        return cartItems;
    }

    public String getItemsTotal() {
        return itemsTotal;
    }
}
