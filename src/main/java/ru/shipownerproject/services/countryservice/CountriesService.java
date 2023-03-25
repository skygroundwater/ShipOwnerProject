package ru.shipownerproject.services.countryservice;

import ru.shipownerproject.models.Country;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.models.Seaman;
import ru.shipownerproject.models.ShipOwner;
import ru.shipownerproject.models.Vessel;

import java.util.List;

public interface CountriesService {

    Country newCountry(Country country);

    boolean deleteCountry(String name);

    Country findCountryByName(String name);

    List<Country> allCountries();

    List<ShipOwner> returnShipOwnersRegisteredInCountry(String name);

    List<Vessel> returnVesselsRegisteredInCountry(String name);

    List<Seaman> seamenWithCitizenShipOfCountry(String name);

    List<Port> portsInCountry(String name);
}
