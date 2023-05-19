package com.parcellocker.parcelhandlerservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Entity
//Még nem tudom, kell-e
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String roleName;

    /*Még nem tudom, hogy kell-e
    //User_roles kapcsolótábla
    //A User osztály a birtokos
    //Kapcsolat a Role és a User között
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    //Courier_roles kapcsolótábla
    //A Courier osztály a birtokos
    //Kapcsolat a Role és a Courier között
    @ManyToMany(mappedBy = "roles")
    private Set<Courier> couriers;
     */
}
