package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;

import java.util.List;

public interface CountriesService {

    String allCountries();

    String newCountry(String name);

    String oneCountry(String name);

    String countryShipOwners(String name);

    String countryVessels(String countryName);

    String refactorCountryName(String oldCountryName, String newCountryName);
}
