package common.pages;

import io.sphere.sdk.models.Base;

public class LinkData extends Base {
    private final String text;
    private final String url;
    private final boolean selected;

    private LinkData(final String text, final String url, final boolean selected) {
        this.text = text;
        this.url = url;
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public boolean isSelected() {
        return selected;
    }

    public static LinkData of(final String text, final String url) {
        return of(text, url, false);
    }

    public static LinkData of(final String text, final String url, final boolean selected) {
        return new LinkData(text, url, selected);
    }
}
