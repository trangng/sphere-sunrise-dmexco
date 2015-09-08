package common.pages;

import io.sphere.sdk.categories.Category;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public class BreadcrumbDataFactory {
    private final CategoryLinkDataFactory categoryLinkDataFactory;

    private BreadcrumbDataFactory(final List<Locale> languages) {
        this.categoryLinkDataFactory = CategoryLinkDataFactory.of(languages);
    }

    public static BreadcrumbDataFactory of(final List<Locale> languages) {
        return new BreadcrumbDataFactory(languages);
    }

    public List<SelectableLinkData> create(final List<Category> categories) {
        final List<SelectableLinkData> breadCrumbData = categories.stream()
                .map(categoryLinkDataFactory::create)
                .collect(toList());
        markLastItemSelected(breadCrumbData);
        return breadCrumbData;
    }

    private void markLastItemSelected(final List<SelectableLinkData> breadCrumbData) {
        breadCrumbData.get(breadCrumbData.size() - 1).setSelected(true);
    }
}
