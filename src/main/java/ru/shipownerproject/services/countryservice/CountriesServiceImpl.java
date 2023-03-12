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

import static ru.shipownerproject.utils.exceptions.ErrorResponse.whatIfEmpty;

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
    public Country findCountryByName(String name) {
        return countriesRepository.findByName(name).stream()
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    @Override
    public List<Country> allCountries() {
        return (List<Country>) whatIfEmpty(findAll(), "of countries for that project");
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
        return (List<ShipOwner>) whatIfEmpty(findById(id).getShipOwners(), "that country's ship owners");
    }

    @Override
    public List<Vessel> countryVessels(Integer id) {
        return (List<Vessel>) whatIfEmpty(findById(id).getVessels(), "that country's vessels");    }

    @Override
    public void refactorCountryName(Integer id, Country newCountry) {
        checkCountryByName(newCountry);
        countriesRepository.save(Stream.of(findById(id)).peek(
                country -> country.setName(newCountry.getName())).findAny().get());
    }
}
