package productcatalog.pages;

import common.categories.CategoryUtils;
import common.pages.LinkData;
import common.pages.ReverseRouter;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import org.junit.Test;
import play.mvc.Call;

import java.util.Locale;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryLinkDataFactoryTest {
    private final CategoryTree categories = CategoryTree.of(CategoryUtils.getQueryResult("categories.json").getResults());

    @Test
    public void createWithPreferredLocales() {
        final Category category = categories.findById("5ebe6dc9-ba32-4030-9f3e-eee0137a1274").get();
        final LinkData linkData =
                CategoryLinkDataFactory.of(reverseRouter(), asList(Locale.ITALIAN, Locale.GERMAN)).create(category);


        assertThat(category.getName().getLocales().stream().findFirst().get()).isEqualTo(Locale.GERMAN);
        assertThat(linkData.getText()).isEqualTo("TestSnowboard equipment it");
        assertThat(linkData.getUrl()).isEqualTo("it/snowboard-equipment")
                .overridingErrorMessage("CategoryLinkDataFactory should choose the first matching preferred locale");
    }

    @Test
    public void createWithoutMatchingPreferredLocale() {
        final Category category = categories.findById("5ebe6dc9-ba32-4030-9f3e-eee0137a1274").get();
        final LinkData linkData = CategoryLinkDataFactory.of(reverseRouter(), emptyList()).create(category);

        assertThat(category.getName().getLocales().stream().findFirst().get()).isEqualTo(Locale.GERMAN);
        assertThat(linkData.getText()).isEqualTo("TestSnowboard equipment de");
        assertThat(linkData.getUrl()).isEqualTo("de/snowboard-equipment")
                .overridingErrorMessage("CategoryLinkDataFactory should fall back to the first locale " +
                        "of the given category, if none of the preferred locales matches.");
    }

    private ReverseRouter reverseRouter() {
        return new DefaultTestReverseRouter() {
            @Override
            public Call category(final String locale, final String slug, final int page) {
                return new Call() {
                    @Override
                    public String url() {
                        return locale + "/" + slug;
                    }

                    @Override
                    public String method() {
                        return null;
                    }

                    @Override
                    public String fragment() {
                        return null;
                    }
                };
            }
        };
    }
}
