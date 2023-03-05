package ru.shipownerproject.services.countryservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountryRepository countryRepository;

    public CountriesServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public static final String NC = "This country is not available.";

    private List<Country> findAll(){
        return countryRepository.findAll();
    }

    private Country findById(Integer id){
        return countryRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    @Override
    public List<Country> allCountries(){
        return findAll();
    }

    @Override
    public void newCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public Country oneCountry(Integer id) {
        return findById(id);
    }

    @Override
    public List<ShipOwner> countryShipOwners(Integer id) {
        return findById(id).getShipOwners();
    }

    @Override
    public List<Vessel> countryVessels(Integer id) {
        return findById(id).getVessels();
    }

    @Override
    public void refactorCountryName(Integer id, Country newCountry) {
        countryRepository.save(
                Stream.of(findById(id)).peek(
                        country -> country.setName(newCountry.getName())).findAny().get());
    }
}
