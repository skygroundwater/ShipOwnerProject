package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;

import java.util.List;

public interface CountriesService {

    List<Country> allCountries();

    Country newCountry(Country country);

    String oneCountry(String name);

    List<ShipOwner> countryShipOwners(String name);

    String countryVessels(String countryName);

    String refactorCountryName(String oldCountryName, String newCountryName);
}
