package productcatalog.pages;

import common.pages.LinkData;
import common.pages.ReverseRouter;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CategoryLinkDataFactory {
    private final ReverseRouter reverseRouter;
    private final List<Locale> languages;

    private CategoryLinkDataFactory(final ReverseRouter reverseRouter, final List<Locale> languages) {
        this.reverseRouter = reverseRouter;
        this.languages = languages;
    }

    public static CategoryLinkDataFactory of(final ReverseRouter reverseRouter, final List<Locale> languages) {
        return new CategoryLinkDataFactory(reverseRouter, languages);
    }

    public LinkData create(final Category category) {
        final String label = getLabel(category);
        final String url = getUrl(category);

        return new LinkData(label, url);
    }

    private String getLabel(final Category category) {
        final Locale locale = getPreferredLocale(category.getName(), languages)
                .orElse(getFallbackLocale(category.getName()));
        return category.getName().get(locale);
    }

    private String getUrl(final Category category) {
        final Locale locale = getPreferredLocale(category.getSlug(), languages)
                .orElse(getFallbackLocale(category.getSlug()));
        return getUrlForLocale(category, locale);
    }

    private Optional<Locale> getPreferredLocale(final LocalizedString localizedString, final Collection<Locale> preferredLocales) {
        return preferredLocales.stream().filter(localizedString.getLocales()::contains).findFirst();
    }

    private Locale getFallbackLocale(final LocalizedString localizedString) {
        return localizedString.getLocales().iterator().next();
    }

    private String getUrlForLocale(final Category category, final Locale locale) {
        return reverseRouter.category(locale.toLanguageTag(), category.getSlug().get(locale), 1).url();
    }
}
