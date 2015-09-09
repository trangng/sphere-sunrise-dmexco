package productcatalog.models;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.SearchSort;

public class SortOptionImpl<T> extends Base implements SortOption<T> {
    private final String label;
    private final String value;
    private final boolean selected;
    private final SearchSort<T> sortModel;

    SortOptionImpl(final String label, final String value, final boolean selected, final SearchSort<T> sortModel) {
        this.label = label;
        this.value = value;
        this.selected = selected;
        this.sortModel = sortModel;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public SearchSort<T> getSortModel() {
        return sortModel;
    }

    @Override
    public SortOption<T> withSelected(final boolean selected) {
        return new SortOptionImpl<>(label, value, selected, sortModel);
    }

    public static <T> SortOption<T> of(final String label, final String value, final boolean selected, final SearchSort<T> sortModel) {
        return new SortOptionImpl<>(label, value, selected, sortModel);
    }
}
