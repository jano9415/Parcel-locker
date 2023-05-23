package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

    //Összes raktár keresése
    @Override
    List<Store> findAll();

    //Keresés id alapján
    @Override
    Optional<Store> findById(Long id);
}
