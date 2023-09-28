package com.parcellocker.statisticsservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Store {

    @Id
    private String id;

    private int postCode;

    private String county;

    private String city;

    private String street;
}
