package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Box;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoxRepository extends CrudRepository<Box, Long> {

    //Összes rekesz keresése
    @Override
    List<Box> findAll();

    //Keresés id alapján
    @Override
    Optional<Box> findById(Long id);

    //Keresés méret alapján
    List<Box> findBySize(String size);
}
