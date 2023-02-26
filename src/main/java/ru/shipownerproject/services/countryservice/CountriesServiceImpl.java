package ru.shipownerproject.services.countryservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;

import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountryRepository countryRepository;

    public CountriesServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public static final String NC = "This country is not available";


    private Country findCountryByName(String countryName){
        return countryRepository.findByName(countryName).stream().findAny().orElse(null);
    }

    @Override
    public List<Country> allCountries(){
        return countryRepository.findAll();
    }

    @Override
    public Country newCountry(Country country) {
        if (findCountryByName(country.getName()) != null) return null;
        return countryRepository.save(country);
    }

    @Override
    public String oneCountry(String countryName) {
        Country country = findCountryByName(countryName);
        if (country == null) return NC;
        return "Country: " + country;
    }

    @Override
    public List<ShipOwner> countryShipOwners(String countryName) {

           return findCountryByName(countryName).getShipOwners();
    }

    @Override
    public String countryVessels(String countryName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            findCountryByName(countryName).getVessels()
                    .forEach(vessel -> stringBuilder.append(vessel).append("\n"));
        }catch (NullPointerException e){
            return NC;
        }
        return String.valueOf(stringBuilder);
    }

    @Override
    public String refactorCountryName(String oldCountryName, String newCountryName) {
        Country country = findCountryByName(oldCountryName);
        if(country == null) return NC;
        country.setName(newCountryName);
        countryRepository.save(country);
        return "Name of this country has been refactored";
    }
}
