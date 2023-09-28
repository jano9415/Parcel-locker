package com.parcellocker.statisticsservice.repository;

import com.parcellocker.statisticsservice.model.ParcelLocker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelLockerRepository extends MongoRepository<ParcelLocker, String> {

}
