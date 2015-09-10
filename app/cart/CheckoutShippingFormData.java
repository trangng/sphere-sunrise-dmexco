package cart;

import com.neovisionaries.i18n.CountryCode;
import common.contexts.UserContext;
import common.pages.SelectableData;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class CheckoutShippingFormData extends Base {
    private final List<CountryCode> allowedCountries = asList(CountryCode.DE);
    private final Cart cart;
    private final UserContext userContext;
    //alpha-2
    private String country;


    public CheckoutShippingFormData(final Cart cart, final UserContext userContext) {
        this.cart = cart;
        this.userContext = userContext;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public List<SelectableData> getSelectableCountries() {
        return allowedCountries.stream()
                .map(countryCode -> {
                    final SelectableData selectableData = new SelectableData();
                    selectableData.setText(countryCode.toLocale().getDisplayCountry(userContext.locale()));
                    selectableData.setValue(countryCode.getAlpha2());
                    selectableData.setSelected(countryCode.getAlpha2().equals(getCountry()));
                    return selectableData;
                })
                .collect(toList());
    }
}
