package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface CountriesService {

    Country findCountryByName(String name);

    Country findCountryByNameWithShipOwners(String name);

    Country findCountryByNameWithVessels(String name);

    List<Country> allCountries();

    void newCountry(Country country);

    List<ShipOwner> countryShipOwners(String name);

    void deleteCountry(String name);

    List<Vessel> countryVessels(String name);

}
