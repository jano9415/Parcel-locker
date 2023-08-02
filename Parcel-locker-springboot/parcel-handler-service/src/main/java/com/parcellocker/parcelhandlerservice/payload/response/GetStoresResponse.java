package com.parcellocker.parcelhandlerservice.payload.response;


import lombok.Data;

@Data
public class GetStoresResponse {

    private Long id;

    private int postCode;

    private String county;

    private String city;

    private String street;
}
