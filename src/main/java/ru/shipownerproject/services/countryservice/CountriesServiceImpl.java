package ru.shipownerproject.services.countryservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountryRepository countryRepository;

    public CountriesServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public static final String NC = "This country is not available";


    private Country findCountryByName(String countryName) {
        return countryRepository.findByName(countryName)
                .stream().findAny().orElse(null);
    }

    private Country findById(Short id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll();
    }

    @Override
    public void newCountry(Country country) {
        if (findCountryByName(country.getName()) != null) return;
        countryRepository.save(country);
    }

    @Override
    public Country oneCountry(Short id) {
        return findById(id);
    }

    @Override
    public List<ShipOwner> countryShipOwners(Short id) {
        return findById(id).getShipOwners();
    }

    @Override
    public List<Vessel> countryVessels(Short id) {
        return findById(id).getVessels();
    }

    @Override
    public void refactorCountryName(Short id, String newCountryName) {
        Country country = findById(id);
        country.setName(newCountryName);
        countryRepository.save(country);
    }
}
