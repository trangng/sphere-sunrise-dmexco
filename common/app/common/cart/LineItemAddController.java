package common.cart;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.utils.Session;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import org.apache.commons.lang3.ObjectUtils;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;

public class LineItemAddController extends CartController {

    @Inject
    public LineItemAddController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> process(final String language) {
        final UserContext userContext = userContext(language);

        final Form<ProductVariantToCartFormData> form = getFilledForm();
        if (form.hasErrors()) {
            return F.Promise.pure(Results.badRequest(form.errorsAsJson()));
        } else {
            final ProductVariantToCartFormData data = form.get();
            final F.Promise<Cart> cartPromise = getOrCreateCart(userContext, Controller.session());
            return cartPromise.flatMap(cart -> {
                final Integer amount = ObjectUtils.firstNonNull(data.getAmount(), 1);
                final AddLineItem action = AddLineItem.of(data.getProductId(), data.getVariantId(), amount);
                return sphere().execute(CartUpdateCommand.of(cart, action))
                .map(cartWithLineItem -> {
                    increaseMiniCartItemCount(amount);
                    return Results.redirect(reverseRouter().product(language, data.getProductSlug(), data.getSku()));
                });
            });
        }
    }

    private void increaseMiniCartItemCount(final Integer amount) {
        final Integer currentAmount = Session.readInt(CartSessionKeys.CART_ITEM_COUNT).orElse(0);
        session().put(CartSessionKeys.CART_ITEM_COUNT, String.valueOf(currentAmount + amount));
    }

    private Form<ProductVariantToCartFormData> getFilledForm() {
        return Form.form(ProductVariantToCartFormData.class).bindFromRequest();
    }

    public static class ProductVariantToCartFormData {
        private String productId;
        private String productSlug;
        private String sku;
        private Integer amount;
        private Integer variantId;

        public ProductVariantToCartFormData() {
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(final String productId) {
            this.productId = productId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(final Integer amount) {
            this.amount = amount;
        }

        public Integer getVariantId() {
            return variantId;
        }

        public void setVariantId(final Integer variantId) {
            this.variantId = variantId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(final String sku) {
            this.sku = sku;
        }

        public String getProductSlug() {
            return productSlug;
        }

        public void setProductSlug(final String productSlug) {
            this.productSlug = productSlug;
        }
    }
}
