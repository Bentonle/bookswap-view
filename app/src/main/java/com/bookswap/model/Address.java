package com.bookswap.model;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("addressLine1")
    private String addressLine1;
    @SerializedName("addressLine2")
    private String addressLine2;
    @SerializedName("addressLine3")
    private String addressLine3;
    @SerializedName("city")
    private String city;
    @SerializedName("province")
    private String province;
    @SerializedName("country")
    private String country;
    @SerializedName("postalCode")
    private String postalCode;

    public Address() {}

    public Address(String addressLine1, String addressLine2, String addressLine3, String city, String province,
                   String country, String postalCode) {
        super();
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
