package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    //Összes user keresése
    @Override
    List<User> findAll();

    //Keresés id alapján
    @Override
    Optional<User> findById(Long id);
}
