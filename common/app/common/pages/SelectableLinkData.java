package common.pages;

public class SelectableLinkData extends LinkData {
    private final boolean selected;

    public SelectableLinkData(final String text, final String url, final boolean selected) {
        super(text, url);
        this.selected = selected;
    }

    public Boolean isSelected() {
        return selected;
    }
}
