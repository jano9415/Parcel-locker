package com.parcellocker.parcelhandlerservice.payload;

import lombok.Data;

@Data
public class ParcelLockerDTO {

    private Long id;

    private int postCode;

    private String county;

    private String city;

    private String street;

    private int amountOfBoxes;





}
