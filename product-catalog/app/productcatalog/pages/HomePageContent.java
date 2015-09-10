package productcatalog.pages;

import common.pages.LinkData;
import common.pages.PageContent;

public class HomePageContent extends PageContent {
    private final LinkData womenLink;
    private final LinkData menLink;

    public HomePageContent(final LinkData womenLink, final LinkData menLink) {
        this.womenLink = womenLink;
        this.menLink = menLink;
    }

    @Override
    public String additionalTitle() {
        return "Home";
    }

    public LinkData getWomenLink() {
        return womenLink;
    }

    public LinkData getMenLink() {
        return menLink;
    }
}
