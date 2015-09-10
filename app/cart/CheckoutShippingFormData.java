package cart;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.Base;
import play.data.validation.Constraints;

public class CheckoutShippingFormData extends Base {

    //alpha-2
    @Constraints.Required
    private String country;
    private String title;
    private String firstName;
    private String lastName;
    private String streetName;
    private String additionalAddressInfo;
    private String city;
    private String postalCode;
    private String region;
    private String phone;
    private String email;


    public CheckoutShippingFormData() {
    }

    public CheckoutShippingFormData(final Cart cart) {
        final Address shippingAddress = cart.getShippingAddress();
        setTitle(shippingAddress.getTitle());
        setCountry(shippingAddress.getCountry().getAlpha2());
        setFirstName(shippingAddress.getFirstName());
        setLastName(shippingAddress.getLastName());
        setStreetName(shippingAddress.getStreetName());
        setAdditionalAddressInfo(shippingAddress.getAdditionalAddressInfo());
        setCity(shippingAddress.getCity());
        setPostalCode(shippingAddress.getPostalCode());
        setRegion(shippingAddress.getRegion());
        setPhone(shippingAddress.getPhone());
        setEmail(shippingAddress.getEmail());
    }

    public Address getShippingAddress() {
        return AddressBuilder.of(getCountryCode())
                .title(getTitle())
                .firstName(getFirstName())
                .lastName(getLastName())
                .streetName(getStreetName())
                .additionalAddressInfo(getAdditionalAddressInfo())
                .city(getCity())
                .postalCode(getPostalCode())
                .region(getRegion())
                .phone(getPhone())
                .email(getEmail())
                .build();
    }

    public String getCountry() {
        return country;
    }

    public CountryCode getCountryCode() {
        return CountryCode.getByCode(getCountry());
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    public void setAdditionalAddressInfo(final String additionalAddressInfo) {
        this.additionalAddressInfo = additionalAddressInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
