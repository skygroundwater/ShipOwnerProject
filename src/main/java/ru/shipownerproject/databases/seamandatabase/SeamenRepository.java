package ru.shipownerproject.databases.seamandatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.seaman.Seaman;

@Repository
public interface SeamenRepository extends JpaRepository<Seaman, Long> {


}
