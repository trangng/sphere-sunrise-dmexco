package productcatalog.pages;

import common.pages.DetailData;
import common.pages.ImageData;
import common.pages.SelectableData;
import io.sphere.sdk.models.Base;

import java.util.List;

public class ProductData extends Base {

    private final String id;
    private final String text;
    private final String sku;
    private final String url;
    private final String slug;
    private final Integer variantId;
    private final String description;
    private final String price;
    private final String priceOld;
    private final List<ImageData> images;
    private final List<SelectableData> colors;
    private final List<SelectableData> sizes;
    private final List<DetailData> details;

    public ProductData(final String text, final String sku, final String description, final String price, final String priceOld, final List<ImageData> images, final List<SelectableData> colors, final List<SelectableData> sizes, final List<DetailData> details, final Integer variantId, final String slug, final String id, final String url) {
        this.text = text;
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.priceOld = priceOld;
        this.images = images;
        this.colors = colors;
        this.sizes = sizes;
        this.details = details;
        this.variantId = variantId;
        this.slug = slug;
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSku() {
        return sku;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public List<ImageData> getImages() {
        return images;
    }

    public List<SelectableData> getColors() {
        return colors;
    }

    public List<SelectableData> getSizes() {
        return sizes;
    }

    public List<DetailData> getDetails() {
        return details;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public String getSlug() {
        return slug;
    }
}
