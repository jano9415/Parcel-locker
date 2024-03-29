package com.parcellocker.statisticsservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ParcelLocker {

    @Id
    private String id;

    private int postCode;

    private String county;

    private String city;

    private String street;
}
