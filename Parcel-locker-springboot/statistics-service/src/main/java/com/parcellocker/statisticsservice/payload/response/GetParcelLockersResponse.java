package com.parcellocker.statisticsservice.payload.response;

import lombok.Data;

@Data
public class GetParcelLockersResponse {

    private Long id;

    private int postCode;

    private String county;

    private String city;

    private String street;

    private int amountOfBoxes;
}
