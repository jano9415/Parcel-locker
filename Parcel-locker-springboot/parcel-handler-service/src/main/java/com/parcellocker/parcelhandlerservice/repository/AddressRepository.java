package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    //Összes cím keresése
    @Override
    List<Address> findAll();

    //Keresés id alapján
    @Override
    Optional<Address> findById(Long id);
}
