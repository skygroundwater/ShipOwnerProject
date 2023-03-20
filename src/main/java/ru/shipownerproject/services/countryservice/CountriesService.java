package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface CountriesService {

    void newCountry(Country country);

    void deleteCountry(String name);

    Country findCountryByName(String name);

    List<Country> allCountries();

    List<ShipOwner> returnShipOwnersRegisteredInCountry(String name);

    List<Vessel> returnVesselsRegisteredInCountry(String name);

    List<Seaman> seamenWithCitizenShipOfCountry(String name);

    List<Port> portsInCountry(String name);
}
