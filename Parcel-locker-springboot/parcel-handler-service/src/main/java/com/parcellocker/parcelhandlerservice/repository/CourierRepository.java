package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Courier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository extends CrudRepository<Courier, Long> {

    //Összes futár keresése
    @Override
    List<Courier> findAll();

    //Keresés id alapján
    @Override
    Optional<Courier> findById(Long id);
}
