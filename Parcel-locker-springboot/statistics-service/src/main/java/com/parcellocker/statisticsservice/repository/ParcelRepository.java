package com.parcellocker.statisticsservice.repository;

import com.parcellocker.statisticsservice.model.Parcel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends MongoRepository<Parcel, String> {

    //Számlálás csomagméret szerint
    int countBySize(String size);

    //Számlálás feladási automata utcanév szerint
    int countByShippingFromStreet(String street);

    //Számlálás érkezési automata utcanév szerint
    int countByShippingToStreet(String street);

    //Számlálás feladási automata megye szerint
    int countByShippingFromCounty(String county);

    //Számlálás érkezési automata megye szerint
    int countByShippingToCounty(String county);


}
