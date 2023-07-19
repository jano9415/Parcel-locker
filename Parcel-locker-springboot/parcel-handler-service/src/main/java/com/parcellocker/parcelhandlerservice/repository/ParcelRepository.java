package com.parcellocker.parcelhandlerservice.repository;

import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Parcel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends CrudRepository<Parcel, Long> {

    //Összes csomag keresése
    @Override
    List<Parcel> findAll();

    //Keresés id alapján
    @Override
    Optional<Parcel> findById(Long id);

    //Keresés átvételi kód szerint
    Parcel findByPickingUpCode(String pickingUpCode);

    //Keresés feladási kód szerint
    Parcel findBySendingCode(String sendingCode);
}
