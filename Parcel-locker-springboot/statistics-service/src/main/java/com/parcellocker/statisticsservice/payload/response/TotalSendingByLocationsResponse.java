package com.parcellocker.statisticsservice.payload.response;

import lombok.Data;

@Data
public class TotalSendingByLocationsResponse {

    private int id;

    private String location;

    private String locationShortFormat;

    private String idAndLocation;

    private int amount;
}
