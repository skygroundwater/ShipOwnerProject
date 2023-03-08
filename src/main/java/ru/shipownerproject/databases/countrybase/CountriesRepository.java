package ru.shipownerproject.databases.countrybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.countries.Country;

import java.util.List;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Integer> {
    List<Country> findByName(String name);
}