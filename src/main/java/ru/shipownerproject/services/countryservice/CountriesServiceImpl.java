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

    private List<Country> findAll(){
        return countryRepository.findAll();
    }

    private Country findCountryByName(String countryName){
        return countryRepository.findByName(countryName).stream().findAny().orElse(null);
    }

    @Override
    public String allCountries(){
        StringBuilder stringBuilder = new StringBuilder();
        findAll().forEach(country -> stringBuilder.append(country).append("\n"));
        return stringBuilder.toString();
    }

    @Override
    public String newCountry(String name) {
        if (findCountryByName(name) != null) return "That country is already added";
        else countryRepository.save(new Country(name));
        return "Country added";
    }

    @Override
    public String oneCountry(String countryName) {
        Country country = findCountryByName(countryName);
        if (country == null) return NC;
        return "Country: " + country;
    }

    @Override
    public String countryShipOwners(String countryName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            findCountryByName(countryName).getShipOwners()
                    .forEach(shipOwner -> stringBuilder.append(shipOwner).append("\n"));
        }catch (NullPointerException e){
            return NC;
        }
           return stringBuilder.toString();
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
