package com.parcellocker.statisticsservice.repository;

import com.parcellocker.statisticsservice.model.Parcel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends MongoRepository<Parcel, String> {

}
