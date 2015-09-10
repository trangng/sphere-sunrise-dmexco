package cart;

import com.neovisionaries.i18n.CountryCode;
import common.contexts.UserContext;
import common.pages.SelectableData;
import io.sphere.sdk.models.Base;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class CheckoutShippingFormContainer extends Base {
    private final List<CountryCode> allowedCountries = asList(CountryCode.DE);
    private final UserContext userContext;
    private CheckoutShippingFormData form;
    private String formAction;

    public CheckoutShippingFormContainer(final UserContext userContext) {
        this.userContext = userContext;
    }

    public CheckoutShippingFormData getForm() {
        return form;
    }

    public void setForm(final CheckoutShippingFormData form) {
        this.form = form;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(final String formAction) {
        this.formAction = formAction;
    }

    public List<SelectableData> getSelectableCountries() {
        return allowedCountries.stream()
                .map(countryCode -> {
                    final SelectableData selectableData = new SelectableData();
                    selectableData.setText(countryCode.toLocale().getDisplayCountry(userContext.locale()));
                    selectableData.setValue(countryCode.getAlpha2());
                    selectableData.setSelected(countryCode.getAlpha2().equals(getForm().getCountry()));
                    return selectableData;
                })
                .collect(toList());
    }

    public List<SelectableData> getSelectableTitles() {
        final String choose = "Choose";
        return asList(choose, "Mr.", "Mrs.", "Ms.", "Dr.").stream()
                .map(title -> {
                    final SelectableData selectableData = new SelectableData();
                    selectableData.setText(title);
                    selectableData.setValue(title);
                    selectableData.setSelected(title.equals(getForm().getTitle()) && !title.equals(choose));
                    return selectableData;
                })
                .collect(toList());
    }
}
