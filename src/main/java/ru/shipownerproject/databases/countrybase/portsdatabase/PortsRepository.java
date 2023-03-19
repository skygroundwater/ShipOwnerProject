package ru.shipownerproject.databases.countrybase.portsdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shipownerproject.models.countries.ports.Port;

import java.util.List;

public interface PortsRepository extends JpaRepository<Port, String> {


    List<Port> findByName(String name);

}
