package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface CountriesService {

    List<Country> allCountries();

    Country newCountry(Country country);

    Country oneCountry(Integer id);

    List<ShipOwner> countryShipOwners(Integer id);

    List<Vessel> countryVessels(Integer id);

    void refactorCountryName(Integer id, Country country);
}
