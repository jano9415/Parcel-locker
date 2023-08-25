package com.example.parcel_locker_android.payload.response;

public class GetParcelLockersResponse {

    private Long id;

    private int postCode;

    private String county;

    private String city;

    private String street;

    private int amountOfBoxes;

    public GetParcelLockersResponse() {
    }

    public GetParcelLockersResponse(Long id, int postCode, String county, String city, String street, int amountOfBoxes) {
        this.id = id;
        this.postCode = postCode;
        this.county = county;
        this.city = city;
        this.street = street;
        this.amountOfBoxes = amountOfBoxes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getAmountOfBoxes() {
        return amountOfBoxes;
    }

    public void setAmountOfBoxes(int amountOfBoxes) {
        this.amountOfBoxes = amountOfBoxes;
    }

    @Override
    public String toString() {
        return postCode + " " + city + " " + street;
    }
}
