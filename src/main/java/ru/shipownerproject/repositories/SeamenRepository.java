package ru.shipownerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shipownerproject.models.seaman.Seaman;

import java.util.List;


public interface SeamenRepository extends JpaRepository<Seaman, Integer> {

    @Query("select seaman from Seaman seaman join fetch seaman.vessel v join fetch v.port " +
            "join fetch seaman.citizenship join fetch seaman.shipowner " +
            "where seaman.passportNumber =:passportNumber")
    List<Seaman> findByPassportNumber(Integer passportNumber);

}
