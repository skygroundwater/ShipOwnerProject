package ru.shipownerproject.services.countryservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountriesRepository countriesRepository;

    public CountriesServiceImpl(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public static final String NC = "This country is not available.";
    public static final String SAME_COUNTRY = "That country";

    private List<Country> findAll() {
        return countriesRepository.findAll();
    }

    private Country findById(Integer id) {
        return countriesRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private void checkCountryByName(Country country) {
        if (countriesRepository.findByName(country.getName()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(SAME_COUNTRY);
    }

    @Override
    public List<Country> allCountries() {
        return findAll();
    }

    @Override
    public Country newCountry(Country country) {
        checkCountryByName(country);
        return countriesRepository.save(country);
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
        checkCountryByName(newCountry);
        countriesRepository.save(Stream.of(findById(id)).peek(
                country -> country.setName(newCountry.getName())).findAny().get());
    }
}
