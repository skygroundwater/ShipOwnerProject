package ru.shipownerproject.databases.countrybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.countries.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Short> {
    List<Country> findByName(String name);
}