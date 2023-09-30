package com.example.parcel_locker_android.payload.response;

public class FollowParcelResponse {

    private String message;

    private int shippingFromPostCode;

    private String shippingFromCounty;

    private String shippingFromCity;

    private String shippingFromStreet;

    private int storePostCode;

    private String storeCounty;

    private String storeCity;

    private String storeStreet;

    private int shippingToPostCode;

    private String shippingToCounty;

    private String shippingToCity;

    private String shippingToStreet;

    private boolean isShipped;

    private boolean isPickedUp;

    private String sendingDate;

    private String sendingTime;

    private String pickingUpDate;

    private String pickingUpTime;

    private String shippingDate;

    private String shippingTime;

    private boolean isPlaced;

    private boolean isPaid;

    private String pickingUpExpirationDate;

    private String pickingUpExpirationTime;

    private boolean isPickingUpExpired;

    private String sendingExpirationDate;

    private String sendingExpirationTime;

    private boolean isSendingExpired;

    private String senderName;

    private String senderEmailAddress;

    private String pickingUpDateFromParcelLockerByCourier;

    private String pickingUpTimeFromParcelLockerByCourier;

    private String handingDateToFirstStoreByCourier;

    private String handingTimeToFirstStoreByCourier;

    private String pickingUpDateFromSecondStoreByCourier;

    private String pickingUpTimeFromSecondStoreByCourier;

    public FollowParcelResponse() {
    }

    public FollowParcelResponse(String message, int shippingFromPostCode, String shippingFromCounty, String shippingFromCity, String shippingFromStreet, int storePostCode, String storeCounty, String storeCity, String storeStreet, int shippingToPostCode, String shippingToCounty, String shippingToCity, String shippingToStreet, boolean isShipped, boolean isPickedUp, String sendingDate, String sendingTime, String pickingUpDate, String pickingUpTime, String shippingDate, String shippingTime, boolean isPlaced, boolean isPaid, String pickingUpExpirationDate, String pickingUpExpirationTime, boolean isPickingUpExpired, String sendingExpirationDate, String sendingExpirationTime, boolean isSendingExpired, String senderName, String senderEmailAddress, String pickingUpDateFromParcelLockerByCourier, String pickingUpTimeFromParcelLockerByCourier, String handingDateToFirstStoreByCourier, String handingTimeToFirstStoreByCourier, String pickingUpDateFromSecondStoreByCourier, String pickingUpTimeFromSecondStoreByCourier) {
        this.message = message;
        this.shippingFromPostCode = shippingFromPostCode;
        this.shippingFromCounty = shippingFromCounty;
        this.shippingFromCity = shippingFromCity;
        this.shippingFromStreet = shippingFromStreet;
        this.storePostCode = storePostCode;
        this.storeCounty = storeCounty;
        this.storeCity = storeCity;
        this.storeStreet = storeStreet;
        this.shippingToPostCode = shippingToPostCode;
        this.shippingToCounty = shippingToCounty;
        this.shippingToCity = shippingToCity;
        this.shippingToStreet = shippingToStreet;
        this.isShipped = isShipped;
        this.isPickedUp = isPickedUp;
        this.sendingDate = sendingDate;
        this.sendingTime = sendingTime;
        this.pickingUpDate = pickingUpDate;
        this.pickingUpTime = pickingUpTime;
        this.shippingDate = shippingDate;
        this.shippingTime = shippingTime;
        this.isPlaced = isPlaced;
        this.isPaid = isPaid;
        this.pickingUpExpirationDate = pickingUpExpirationDate;
        this.pickingUpExpirationTime = pickingUpExpirationTime;
        this.isPickingUpExpired = isPickingUpExpired;
        this.sendingExpirationDate = sendingExpirationDate;
        this.sendingExpirationTime = sendingExpirationTime;
        this.isSendingExpired = isSendingExpired;
        this.senderName = senderName;
        this.senderEmailAddress = senderEmailAddress;
        this.pickingUpDateFromParcelLockerByCourier = pickingUpDateFromParcelLockerByCourier;
        this.pickingUpTimeFromParcelLockerByCourier = pickingUpTimeFromParcelLockerByCourier;
        this.handingDateToFirstStoreByCourier = handingDateToFirstStoreByCourier;
        this.handingTimeToFirstStoreByCourier = handingTimeToFirstStoreByCourier;
        this.pickingUpDateFromSecondStoreByCourier = pickingUpDateFromSecondStoreByCourier;
        this.pickingUpTimeFromSecondStoreByCourier = pickingUpTimeFromSecondStoreByCourier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getShippingFromPostCode() {
        return shippingFromPostCode;
    }

    public void setShippingFromPostCode(int shippingFromPostCode) {
        this.shippingFromPostCode = shippingFromPostCode;
    }

    public String getShippingFromCounty() {
        return shippingFromCounty;
    }

    public void setShippingFromCounty(String shippingFromCounty) {
        this.shippingFromCounty = shippingFromCounty;
    }

    public String getShippingFromCity() {
        return shippingFromCity;
    }

    public void setShippingFromCity(String shippingFromCity) {
        this.shippingFromCity = shippingFromCity;
    }

    public String getShippingFromStreet() {
        return shippingFromStreet;
    }

    public void setShippingFromStreet(String shippingFromStreet) {
        this.shippingFromStreet = shippingFromStreet;
    }

    public int getStorePostCode() {
        return storePostCode;
    }

    public void setStorePostCode(int storePostCode) {
        this.storePostCode = storePostCode;
    }

    public String getStoreCounty() {
        return storeCounty;
    }

    public void setStoreCounty(String storeCounty) {
        this.storeCounty = storeCounty;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreStreet() {
        return storeStreet;
    }

    public void setStoreStreet(String storeStreet) {
        this.storeStreet = storeStreet;
    }

    public int getShippingToPostCode() {
        return shippingToPostCode;
    }

    public void setShippingToPostCode(int shippingToPostCode) {
        this.shippingToPostCode = shippingToPostCode;
    }

    public String getShippingToCounty() {
        return shippingToCounty;
    }

    public void setShippingToCounty(String shippingToCounty) {
        this.shippingToCounty = shippingToCounty;
    }

    public String getShippingToCity() {
        return shippingToCity;
    }

    public void setShippingToCity(String shippingToCity) {
        this.shippingToCity = shippingToCity;
    }

    public String getShippingToStreet() {
        return shippingToStreet;
    }

    public void setShippingToStreet(String shippingToStreet) {
        this.shippingToStreet = shippingToStreet;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getPickingUpDate() {
        return pickingUpDate;
    }

    public void setPickingUpDate(String pickingUpDate) {
        this.pickingUpDate = pickingUpDate;
    }

    public String getPickingUpTime() {
        return pickingUpTime;
    }

    public void setPickingUpTime(String pickingUpTime) {
        this.pickingUpTime = pickingUpTime;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPickingUpExpirationDate() {
        return pickingUpExpirationDate;
    }

    public void setPickingUpExpirationDate(String pickingUpExpirationDate) {
        this.pickingUpExpirationDate = pickingUpExpirationDate;
    }

    public String getPickingUpExpirationTime() {
        return pickingUpExpirationTime;
    }

    public void setPickingUpExpirationTime(String pickingUpExpirationTime) {
        this.pickingUpExpirationTime = pickingUpExpirationTime;
    }

    public boolean isPickingUpExpired() {
        return isPickingUpExpired;
    }

    public void setPickingUpExpired(boolean pickingUpExpired) {
        isPickingUpExpired = pickingUpExpired;
    }

    public String getSendingExpirationDate() {
        return sendingExpirationDate;
    }

    public void setSendingExpirationDate(String sendingExpirationDate) {
        this.sendingExpirationDate = sendingExpirationDate;
    }

    public String getSendingExpirationTime() {
        return sendingExpirationTime;
    }

    public void setSendingExpirationTime(String sendingExpirationTime) {
        this.sendingExpirationTime = sendingExpirationTime;
    }

    public boolean isSendingExpired() {
        return isSendingExpired;
    }

    public void setSendingExpired(boolean sendingExpired) {
        isSendingExpired = sendingExpired;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    public void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }

    public String getPickingUpDateFromParcelLockerByCourier() {
        return pickingUpDateFromParcelLockerByCourier;
    }

    public void setPickingUpDateFromParcelLockerByCourier(String pickingUpDateFromParcelLockerByCourier) {
        this.pickingUpDateFromParcelLockerByCourier = pickingUpDateFromParcelLockerByCourier;
    }

    public String getPickingUpTimeFromParcelLockerByCourier() {
        return pickingUpTimeFromParcelLockerByCourier;
    }

    public void setPickingUpTimeFromParcelLockerByCourier(String pickingUpTimeFromParcelLockerByCourier) {
        this.pickingUpTimeFromParcelLockerByCourier = pickingUpTimeFromParcelLockerByCourier;
    }

    public String getHandingDateToFirstStoreByCourier() {
        return handingDateToFirstStoreByCourier;
    }

    public void setHandingDateToFirstStoreByCourier(String handingDateToFirstStoreByCourier) {
        this.handingDateToFirstStoreByCourier = handingDateToFirstStoreByCourier;
    }

    public String getHandingTimeToFirstStoreByCourier() {
        return handingTimeToFirstStoreByCourier;
    }

    public void setHandingTimeToFirstStoreByCourier(String handingTimeToFirstStoreByCourier) {
        this.handingTimeToFirstStoreByCourier = handingTimeToFirstStoreByCourier;
    }

    public String getPickingUpDateFromSecondStoreByCourier() {
        return pickingUpDateFromSecondStoreByCourier;
    }

    public void setPickingUpDateFromSecondStoreByCourier(String pickingUpDateFromSecondStoreByCourier) {
        this.pickingUpDateFromSecondStoreByCourier = pickingUpDateFromSecondStoreByCourier;
    }

    public String getPickingUpTimeFromSecondStoreByCourier() {
        return pickingUpTimeFromSecondStoreByCourier;
    }

    public void setPickingUpTimeFromSecondStoreByCourier(String pickingUpTimeFromSecondStoreByCourier) {
        this.pickingUpTimeFromSecondStoreByCourier = pickingUpTimeFromSecondStoreByCourier;
    }
}
