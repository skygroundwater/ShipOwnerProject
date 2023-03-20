package ru.shipownerproject.services.countryservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.ListIsEmptyException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.List;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.whatIfEmpty;

@Service
public class CountriesServiceImpl implements CountriesService {

    private final CountriesRepository countriesRepository;

    public CountriesServiceImpl(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public static final String NC = "This country is not available.";

    public static final String THAT_COUNTRY = "That country";

    private List<Country> findAll() {
        return countriesRepository.findAll();
    }

    private void checkCountryByName(Country country) {
        if (countriesRepository.findByName(country.getName()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(THAT_COUNTRY);
    }

    private Country findCountryByNameWithShipOwners(String name) {
        return countriesRepository.findByNameWithShipOwners(name).stream()
                .findAny().orElseThrow(() -> new ListIsEmptyException(THAT_COUNTRY));
    }

    private Country findCountryByNameWithVessels(String name) {
        return countriesRepository.findByNameWithVessels(name).stream()
                .findAny().orElseThrow(() -> new ListIsEmptyException(THAT_COUNTRY));
    }

    private Country findCountryByNameWithSeamen(String name) {
        return countriesRepository.findByNameWithSeamen(name).stream()
                .findAny().orElseThrow(() -> new ListIsEmptyException(THAT_COUNTRY));
    }

    private Country findCountryByNameWithPorts(String name) {
        return countriesRepository.findByNameWithPorts(name).stream()
                .findAny().orElseThrow(() -> new ListIsEmptyException(THAT_COUNTRY));
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
    public void newCountry(Country country) {
        checkCountryByName(country);
        countriesRepository.save(country);
    }

    @Override
    public void deleteCountry(String name) {
        countriesRepository.delete(findCountryByName(name));
    }

    @Override
    public List<ShipOwner> returnShipOwnersRegisteredInCountry(String name) {
        return findCountryByNameWithShipOwners(name).getShipOwners();
    }

    @Override
    public List<Vessel> returnVesselsRegisteredInCountry(String name) {
        return findCountryByNameWithVessels(name).getVessels();
    }

    @Override
    public List<Seaman> seamenWithCitizenShipOfCountry(String name){
        return findCountryByNameWithSeamen(name).getSeamen();
    }

    @Override
    public List<Port> portsInCountry(String name){
        return findCountryByNameWithPorts(name).getPorts();
    }
}