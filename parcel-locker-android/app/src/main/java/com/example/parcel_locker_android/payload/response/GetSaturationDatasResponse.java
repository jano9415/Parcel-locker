package com.example.parcel_locker_android.payload.response;

public class GetSaturationDatasResponse {

    private int amountOfBoxes;

    private int amountOfSmallBoxes;

    private int amountOfMediumBoxes;

    private int amountOfLargeBoxes;

    private int amountOfFullSmallBoxes;

    private int amountOfFullMediumBoxes;

    private int amountOfFullLargeBoxes;

    public GetSaturationDatasResponse() {
    }

    public GetSaturationDatasResponse(int amountOfBoxes, int amountOfSmallBoxes, int amountOfMediumBoxes, int amountOfLargeBoxes, int amountOfFullSmallBoxes, int amountOfFullMediumBoxes, int amountOfFullLargeBoxes) {
        this.amountOfBoxes = amountOfBoxes;
        this.amountOfSmallBoxes = amountOfSmallBoxes;
        this.amountOfMediumBoxes = amountOfMediumBoxes;
        this.amountOfLargeBoxes = amountOfLargeBoxes;
        this.amountOfFullSmallBoxes = amountOfFullSmallBoxes;
        this.amountOfFullMediumBoxes = amountOfFullMediumBoxes;
        this.amountOfFullLargeBoxes = amountOfFullLargeBoxes;
    }

    public int getAmountOfBoxes() {
        return amountOfBoxes;
    }

    public void setAmountOfBoxes(int amountOfBoxes) {
        this.amountOfBoxes = amountOfBoxes;
    }

    public int getAmountOfSmallBoxes() {
        return amountOfSmallBoxes;
    }

    public void setAmountOfSmallBoxes(int amountOfSmallBoxes) {
        this.amountOfSmallBoxes = amountOfSmallBoxes;
    }

    public int getAmountOfMediumBoxes() {
        return amountOfMediumBoxes;
    }

    public void setAmountOfMediumBoxes(int amountOfMediumBoxes) {
        this.amountOfMediumBoxes = amountOfMediumBoxes;
    }

    public int getAmountOfLargeBoxes() {
        return amountOfLargeBoxes;
    }

    public void setAmountOfLargeBoxes(int amountOfLargeBoxes) {
        this.amountOfLargeBoxes = amountOfLargeBoxes;
    }

    public int getAmountOfFullSmallBoxes() {
        return amountOfFullSmallBoxes;
    }

    public void setAmountOfFullSmallBoxes(int amountOfFullSmallBoxes) {
        this.amountOfFullSmallBoxes = amountOfFullSmallBoxes;
    }

    public int getAmountOfFullMediumBoxes() {
        return amountOfFullMediumBoxes;
    }

    public void setAmountOfFullMediumBoxes(int amountOfFullMediumBoxes) {
        this.amountOfFullMediumBoxes = amountOfFullMediumBoxes;
    }

    public int getAmountOfFullLargeBoxes() {
        return amountOfFullLargeBoxes;
    }

    public void setAmountOfFullLargeBoxes(int amountOfFullLargeBoxes) {
        this.amountOfFullLargeBoxes = amountOfFullLargeBoxes;
    }
}
