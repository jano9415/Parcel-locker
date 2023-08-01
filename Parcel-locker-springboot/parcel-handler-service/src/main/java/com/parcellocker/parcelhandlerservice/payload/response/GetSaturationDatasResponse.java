package com.parcellocker.parcelhandlerservice.payload.response;

import lombok.Data;

@Data
public class GetSaturationDatasResponse {

    private int amountOfBoxes;

    private int amountOfSmallBoxes;

    private int amountOfMediumBoxes;

    private int amountOfLargeBoxes;

    private int amountOfFullSmallBoxes;

    private int amountOfFullMediumBoxes;

    private int amountOfFullLargeBoxes;
}
