package ru.shipownerproject.databases.seamandatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.seaman.Seaman;

import java.util.List;

@Repository
public interface SeamenRepository extends JpaRepository<Seaman, Integer> {

    List<Seaman> findByPassportNumber(Integer passportNumber);

}
