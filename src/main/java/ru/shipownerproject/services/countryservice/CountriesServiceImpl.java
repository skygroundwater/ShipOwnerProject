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
    public List<ShipOwner> countryShipOwners(String name) {
        return (List<ShipOwner>) whatIfEmpty(findCountryByName(name).getShipOwners(),
                "that country's ship owners");
    }

    @Override
    public List<Vessel> countryVessels(String name) {
        return (List<Vessel>) whatIfEmpty(findCountryByName(name).getVessels(),
                "that country's vessels");
    }

    @Override
    public void refactorCountryName(String name, Country newCountry) {
        checkCountryByName(newCountry);
        countriesRepository.save(Stream.of(findCountryByName(name)).peek(
                country -> country.setName(newCountry.getName())).findAny().get());
    }
}
