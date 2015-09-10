package cart;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.Base;
import play.data.validation.Constraints;

import javax.annotation.Nullable;
import java.util.Optional;

public class CheckoutShippingFormData extends Base {

    private String title;
    @Constraints.Required
    private String firstName;
    @Constraints.Required
    private String lastName;
    //alpha-2
    @Constraints.Required
    private String country;
    @Constraints.Required
    private String streetName;
    private String additionalAddressInfo;
    @Constraints.Required
    private String city;
    @Constraints.Required
    private String postalCode;
    private String region;
    private String phone;
    @Constraints.Required
    private String email;

    //billing stuff
    //alpha-2
    private String billingCountry;
    private String billingStreetName;
    private String billingAdditionalAddressInfo;
    private String billingCity;
    private String billingPostalCode;
    private String billingRegion;


    public CheckoutShippingFormData() {
    }

    public CheckoutShippingFormData(final Cart cart) {
        setShippingAddress(cart.getShippingAddress());
        Optional.ofNullable(cart.getBillingAddress()).ifPresent(b -> {
            setBillingCountry(b.getCountry().getAlpha2());
            setBillingStreetName(b.getStreetName());
            setBillingAdditionalAddressInfo(b.getAdditionalAddressInfo());
            setBillingCity(b.getCity());
            setBillingPostalCode(b.getPostalCode());
            setBillingRegion(b.getRegion());
        });
    }

    private void setShippingAddress(final Address shippingAddress) {
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

    @Nullable
    public Address getBillingAddress() {
        return isDifferentAddressForBilling() ?
                AddressBuilder.of(getBillingCountryCode())
                        .title(getTitle())
                        .firstName(getFirstName())
                        .lastName(getLastName())
                        .streetName(getBillingStreetName())
                        .additionalAddressInfo(getBillingAdditionalAddressInfo())
                        .city(getBillingCity())
                        .postalCode(getBillingPostalCode())
                        .region(getBillingRegion())
                        .build()
                : null;
    }

    public String getCountry() {
        return country;
    }

    public CountryCode getCountryCode() {
        final String country = getCountry();
        return country != null ? CountryCode.getByCode(country) : null;
    }

    public CountryCode getBillingCountryCode() {
        final String country = getBillingCountry();
        return country != null ? CountryCode.getByCode(country) : null;
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

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(final String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingStreetName() {
        return billingStreetName;
    }

    public void setBillingStreetName(final String billingStreetName) {
        this.billingStreetName = billingStreetName;
    }

    public String getBillingAdditionalAddressInfo() {
        return billingAdditionalAddressInfo;
    }

    public void setBillingAdditionalAddressInfo(final String billingAdditionalAddressInfo) {
        this.billingAdditionalAddressInfo = billingAdditionalAddressInfo;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(final String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingPostalCode() {
        return billingPostalCode;
    }

    public void setBillingPostalCode(final String billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }

    public String getBillingRegion() {
        return billingRegion;
    }

    public void setBillingRegion(final String billingRegion) {
        this.billingRegion = billingRegion;
    }

    public boolean isDifferentAddressForBilling() {
        return getBillingCountry() != null;
    }
}
