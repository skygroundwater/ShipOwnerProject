package ru.shipownerproject.databases.countrybase.portsdatabase;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.countries.ports.Port;

@Repository
public interface PortsRepository extends JpaRepository<Port, Integer> {


}
