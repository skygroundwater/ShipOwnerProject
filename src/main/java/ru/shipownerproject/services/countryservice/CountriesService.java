package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface CountriesService {

    List<Country> allCountries();

    void newCountry(Country country);

    Country oneCountry(Short id);

    List<ShipOwner> countryShipOwners(Short id);

    List<Vessel> countryVessels(Short id);

    void refactorCountryName(Short id, String newCountryName);
}
