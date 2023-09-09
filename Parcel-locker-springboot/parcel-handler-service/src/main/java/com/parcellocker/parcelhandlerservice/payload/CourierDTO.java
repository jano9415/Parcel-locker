package com.parcellocker.parcelhandlerservice.payload;

import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.Store;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CourierDTO {


    private Long id;

    private String uniqueCourierId;

    private  String firstName;

    private String lastName;

    private int storePostCode;

    private String storeCounty;

    private String storeCity;

    private String storeStreet;
}
