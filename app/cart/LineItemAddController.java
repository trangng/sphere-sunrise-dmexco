package cart;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.AddLineItem;
import play.data.Form;
import play.libs.F;
import play.mvc.Result;

import javax.inject.Inject;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

/**
 * Adds a line item to a cart. Redirects to the product detail page in the success case.
 */
public class LineItemAddController extends CartController {

    @Inject
    public LineItemAddController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> process(final String language) {
        final UserContext userContext = userContext(language);
        final Form<ProductVariantToCartFormData> form = getFilledForm();
        if (form.hasErrors()) {
            return F.Promise.pure(badRequest(form.errorsAsJson()));
        } else {
            final ProductVariantToCartFormData data = form.get();
            final F.Promise<Cart> cartPromise = getOrCreateCart(userContext, session());
            return cartPromise.flatMap(cart -> {
                final Long itemCount = firstNonNull(data.getAmount(), 1L);
                final AddLineItem action = AddLineItem.of(data.getProductId(), data.getVariantId(), itemCount);
                return sphere().execute(CartUpdateCommand.of(cart, action))
                        .map(cartWithLineItem -> {
                            CartSessionUtils.overwriteCartSessionData(cartWithLineItem, session());
                            return redirect(reverseRouter().product(language, data.getProductSlug(), data.getSku()));
                        });
            });
        }
    }

    private Form<ProductVariantToCartFormData> getFilledForm() {
        return Form.form(ProductVariantToCartFormData.class).bindFromRequest();
    }

    public static class ProductVariantToCartFormData {
        private String productId;
        private String productSlug;
        private String sku;
        private Long amount;
        private Integer variantId;

        public ProductVariantToCartFormData() {
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(final String productId) {
            this.productId = productId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(final Long amount) {
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
