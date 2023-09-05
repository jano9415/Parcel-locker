package com.parcellocker.statisticsservice.payload.response;

import lombok.Data;

@Data
public class TotalSendingByLocationsResponse {

    private String location;

    private int amount;
}
