package com.parcellocker.statisticsservice.repository;

import com.parcellocker.statisticsservice.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends MongoRepository<Store, String> {

}
