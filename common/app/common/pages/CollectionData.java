package common.pages;

import io.sphere.sdk.models.Base;

import java.util.List;

public class CollectionData<T> extends Base {
    private final String text;
    private final List<T> list;

    public CollectionData(final String text, final List<T> list) {
        this.text = text;
        this.list = list;
    }

    public String getText() {
        return text;
    }

    public List<T> getList() {
        return list;
    }
}
