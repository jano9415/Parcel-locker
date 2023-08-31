package com.parcellocker.statisticsservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelLocker {

    private int postCode;

    private String county;

    private String city;

    private String street;
}
