package common.pages;

import io.sphere.sdk.models.Base;
import play.i18n.Messages;

public class PageHeader extends Base {
    private final Messages messages;
    private final String title;
    private final CollectionData<SelectableData> countries;
    private final NavMenuData navMenuData;
    private final MiniCartData miniCartData;
    private final String searchUrl;
    private final String queryParamName;

    public PageHeader(final Messages messages, final String title, final CollectionData<SelectableData> countries, final NavMenuData navMenuData, final MiniCartData miniCartData, final String searchUrl, final String queryParamName) {
        this.messages = messages;
        this.countries = countries;
        this.title = title;
        this.navMenuData = navMenuData;
        this.miniCartData = miniCartData;
        this.searchUrl = searchUrl;
        this.queryParamName = queryParamName;
    }

    public String getTitle() {
        return title;
    }

    public LinkData getStores() {
        return new LinkData(messages.at("header.stores"), "");
    }

    public LinkData getHelp() {
        return new LinkData(messages.at("header.help"), "");
    }

    public LinkData getCallUs() {
        return new LinkData(messages.at("header.callUs"), "");
    }

    public NavMenuData getNavMenu() {
        return navMenuData;
    }

    public MiniCartData getMiniCart() {
        return miniCartData;
    }

    public CollectionData getCountries() {
        return countries;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public String getSearchInputName() {
        return queryParamName;
    }
}
