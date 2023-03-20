package ru.shipownerproject.databases.countrybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shipownerproject.models.countries.Country;

import java.util.List;

public interface CountriesRepository extends JpaRepository<Country, String> {
    List<Country> findByName(String name);

    @Query("select c from Country c join fetch c.shipOwners where c.name =:name")
    List<Country> findByNameWithShipOwners(String name);

    @Query("select c from Country c join fetch c.vessels v join fetch v.shipOwner join fetch v.port where c.name =:name")
    List<Country> findByNameWithVessels(String name);

    @Query("select c from Country c join fetch c.seamen s join s.vessel v join s.shipowner join v.shipOwner where c.name =:name")
    List<Country> findByNameWithSeamen(String name);

    @Query("select c from Country c join fetch c.ports where c.name =:name")
    List<Country> findByNameWithPorts(String name);

}