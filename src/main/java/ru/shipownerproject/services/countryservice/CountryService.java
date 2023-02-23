package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;

public interface CountryService {

    String newCountry(String name);

    String oneCountry(String name);

    Country findByName(String name);

    String countryShipOwners(String name);

    String countryVessels(Integer id);

    String refactorCountryName(String oldCountryName, String newCountryName);
}
