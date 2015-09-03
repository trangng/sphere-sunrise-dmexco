package productcatalog.pages;

import common.contexts.PriceFinderFactory;
import common.contexts.UserContext;
import common.pages.DetailData;
import common.pages.ImageData;
import common.pages.ReverseRouter;
import common.pages.SelectableData;
import common.prices.PriceFinder;
import common.utils.PriceFormatter;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeAccess;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;

public class ProductDataFactory {

    private static final AttributeAccess<String> TEXT_ATTR_ACCESS = AttributeAccess.ofText();
    private static final AttributeAccess<LocalizedEnumValue> LENUM_ATTR_ACCESS = AttributeAccess.ofLocalizedEnumValue();
    private static final AttributeAccess<Set<LocalizedString>> LENUM_SET_ATTR_ACCESS = AttributeAccess.ofLocalizedStringSet();

    private final UserContext userContext;
    private final PriceFormatter priceFormatter;
    private final PriceFinder priceFinder;
    private final ReverseRouter reverseRouter;

    private ProductDataFactory(final UserContext userContext, final ReverseRouter reverseRouter) {
        this.userContext = userContext;
        this.priceFormatter = PriceFormatter.of(userContext.locale());
        this.priceFinder = PriceFinderFactory.create(userContext);
        this.reverseRouter = reverseRouter;
    }

    public static ProductDataFactory of(final UserContext userContext, final ReverseRouter reverseRouter) {
        return new ProductDataFactory(userContext, reverseRouter);
    }

    public ProductData create(final ProductProjection product, final ProductVariant variant) {
        final Optional<Price> priceOpt = priceFinder.findPrice(variant.getPrices());
        final String name = product.getName().find(userContext.locales()).orElse("");
        final String sku = Optional.ofNullable(variant.getSku()).orElse("");
        final String slug = product.getSlug().find(userContext.locales()).orElse("");
        final String url = reverseRouter.product(userContext.locale().toLanguageTag(), slug, sku).url();
        final String description = Optional.ofNullable(product.getDescription()).flatMap(d -> d.find(userContext.locales())).orElse("");
        final String price = getPriceCurrent(priceOpt).map(p -> priceFormatter.format(p.getValue())).orElse("");
        final String priceOld = getPriceOld(priceOpt).map(p -> priceFormatter.format(p.getValue())).orElse("");
        return new ProductData(name, sku, url, description, price, priceOld, getImages(variant), getColors(product), getSizes(product), getDetails(variant));
    }

    private List<ImageData> getImages(final ProductVariant productVariant) {
        final List<ImageData> images = productVariant.getImages().stream().map(ImageData::of).collect(toList());
        if(images.isEmpty()) {
            images.add(getPlaceholderImage());
        }

        return images;
    }

    private ImageData getPlaceholderImage() {
        return ImageData.of(Image.of("http://placehold.it/300x400", ImageDimensions.of(300, 400)));
    }

    private List<SelectableData> getColors(final ProductProjection product) {
        return getColorInAllVariants(product).stream()
                .map(this::colorToSelectableItem)
                .collect(toList());
    }

    private List<SelectableData> getSizes(final ProductProjection product) {
        return getSizeInAllVariants(product).stream()
                .map(this::sizeToSelectableItem)
                .collect(toList());
    }

    private List<DetailData> getDetails(final ProductVariant variant) {
        return variant.findAttribute("details", LENUM_SET_ATTR_ACCESS).orElse(emptySet()).stream()
                .map(this::localizedStringsToDetailData)
                .collect(toList());
    }

    private List<Attribute> getColorInAllVariants(final ProductProjection product) {
        return getAttributeInAllVariants(product, "color");
    }

    private List<Attribute> getSizeInAllVariants(final ProductProjection product) {
        return getAttributeInAllVariants(product, "size");
    }

    private List<Attribute> getAttributeInAllVariants(final ProductProjection product, final String attributeName) {
        return product.getAllVariants().stream()
                .map(variant -> Optional.ofNullable(variant.getAttribute(attributeName)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .collect(toList());
    }

    private SelectableData colorToSelectableItem(final Attribute color) {
        final String colorLabel = color.getValue(LENUM_ATTR_ACCESS).getLabel().find(userContext.locales()).orElse("");
        return new SelectableData(colorLabel, color.getName(), "", "", false);
    }

    private SelectableData sizeToSelectableItem(final Attribute size) {
        final String sizeLabel = size.getValue(TEXT_ATTR_ACCESS);
        return new SelectableData(sizeLabel, sizeLabel, "", "", false);
    }

    private DetailData localizedStringsToDetailData(final LocalizedString localizedStrings) {
        final String label = localizedStrings.find(userContext.locales()).orElse("");
        return new DetailData(label, "");
    }

    private Optional<Price> getPriceOld(final Optional<Price> priceOpt) {
        return priceOpt.flatMap(price -> Optional.ofNullable(price.getDiscounted()).map(discountedPrice -> price));
    }

    private Optional<Price> getPriceCurrent(final Optional<Price> priceOpt) {
        return priceOpt.map(price -> Optional.ofNullable(price.getDiscounted()).map(DiscountedPrice::getValue)
                .orElse(price.getValue()))
                .map(Price::of);
    }
}
