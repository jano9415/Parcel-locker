package com.parcellocker.parcelhandlerservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Box {

    private Long id;

    private int maxWidth;

    private int maxLength;

    private int maxHeight;

    private int maxWeight;

    private int boxNumber;
}
