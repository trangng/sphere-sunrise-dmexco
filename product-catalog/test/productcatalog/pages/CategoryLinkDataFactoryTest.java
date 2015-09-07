package productcatalog.pages;

import common.categories.CategoryUtils;
import common.pages.LinkData;
import common.pages.ReverseRouter;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import org.junit.Test;
import play.mvc.Call;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryLinkDataFactoryTest {
    private final CategoryTree categories = CategoryTree.of(CategoryUtils.getQueryResult("categories.json").getResults());

    @Test
    public void create() {
        final Category category = categories.findById("5ebe6dc9-ba32-4030-9f3e-eee0137a1274").get();
        final LinkData linkData = CategoryLinkDataFactory.of().create(category);

        assertThat(linkData.getText()).isEqualTo("TestSnowboard equipment");
        assertThat(linkData.getUrl()).isEqualTo("");
    }

    private ReverseRouter reverseRouter() {
        return new ReverseRouter() {

            @Override
            public Call category(final String locale, final String slug, final int page) {
                
                return productcatalog.controllers.routes.ProductOverviewPageController.show(locale, slug, page);
            }

            @Override
            public Call product(final String locale, final String productSlug, final String sku) {
                return null;
            }
        };
    }
}
