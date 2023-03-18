package ru.shipownerproject.databases.countrybase.portsdatabase;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.countries.ports.Port;

import java.util.List;

@Repository
public interface PortsRepository extends JpaRepository<Port, String> {

    List<Port> findByName(String name);

}
