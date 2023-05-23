package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelLockerRepository extends CrudRepository<ParcelLocker, Long> {

    //Összes csomag automata keresése
    @Override
    List<ParcelLocker> findAll();

    //Keresés id alapján
    @Override
    Optional<ParcelLocker> findById(Long id);
}
