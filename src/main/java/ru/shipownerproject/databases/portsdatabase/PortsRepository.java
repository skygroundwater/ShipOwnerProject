package ru.shipownerproject.databases.portsdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shipownerproject.models.countries.ports.Port;

import java.util.List;

public interface PortsRepository extends JpaRepository<Port, String> {

    List<Port> findByName(String name);

    @Query("select p from Port p join fetch p.regVessels v join fetch v.shipOwner where p.name =:name")
    List<Port> findByNameWithVessels(String name);

}
