package com.parcellocker.statisticsservice.payload.response;

import lombok.Data;

@Data
public class StoreTurnOverDataResponse {

    private int id;

    private String county;

    private String location;

    private int amount;
}
