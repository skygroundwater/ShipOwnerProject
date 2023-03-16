package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface CountriesService {

    Country findCountryByName(String name);

    List<Country> allCountries();

    Country newCountry(Country country);

    List<ShipOwner> countryShipOwners(String name);

    List<Vessel> countryVessels(String name);

    void refactorCountryName(String name, Country country);
}
