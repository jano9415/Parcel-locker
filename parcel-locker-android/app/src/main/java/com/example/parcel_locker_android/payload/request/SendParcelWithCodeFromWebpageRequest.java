package com.example.parcel_locker_android.payload.request;

public class SendParcelWithCodeFromWebpageRequest {
    private int price;

    private String size;

    private Long parcelLockerFromId;

    private Long parcelLockerToId;

    private String receiverName;

    private String receiverEmailAddress;

    private String receiverPhoneNumber;

    private String senderEmailAddress;

    public SendParcelWithCodeFromWebpageRequest() {
    }

    public SendParcelWithCodeFromWebpageRequest(int price, String size, Long parcelLockerFromId, Long parcelLockerToId, String receiverName, String receiverEmailAddress, String receiverPhoneNumber, String senderEmailAddress) {
        this.price = price;
        this.size = size;
        this.parcelLockerFromId = parcelLockerFromId;
        this.parcelLockerToId = parcelLockerToId;
        this.receiverName = receiverName;
        this.receiverEmailAddress = receiverEmailAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.senderEmailAddress = senderEmailAddress;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getParcelLockerFromId() {
        return parcelLockerFromId;
    }

    public void setParcelLockerFromId(Long parcelLockerFromId) {
        this.parcelLockerFromId = parcelLockerFromId;
    }

    public Long getParcelLockerToId() {
        return parcelLockerToId;
    }

    public void setParcelLockerToId(Long parcelLockerToId) {
        this.parcelLockerToId = parcelLockerToId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverEmailAddress() {
        return receiverEmailAddress;
    }

    public void setReceiverEmailAddress(String receiverEmailAddress) {
        this.receiverEmailAddress = receiverEmailAddress;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    public void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }
}
