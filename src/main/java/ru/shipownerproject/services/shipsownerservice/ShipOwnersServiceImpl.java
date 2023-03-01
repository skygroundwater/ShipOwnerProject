package ru.shipownerproject.services.shipsownerservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;
import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;

@Service
public class ShipOwnersServiceImpl implements ShipOwnersService {

    private final ShipOwnerRepository shipOwnerRepository;
    private final CountryRepository countryRepository;

    public ShipOwnersServiceImpl(ShipOwnerRepository shipOwnerRepository, CountryRepository countryRepository) {
        this.shipOwnerRepository = shipOwnerRepository;
        this.countryRepository = countryRepository;
    }

    public static final String NS = "This Ship Owner is not available";

    private ShipOwner findShipOwnerByName(String shipOwnerName) {
        return shipOwnerRepository.findByName(shipOwnerName).stream().findAny().orElse(null);
    }

    private Country findCountryByName(String countryName) {
        return countryRepository.findByName(countryName).stream().findAny().orElse(null);
    }

    @Override
    public String shipOwner(String name) {
        ShipOwner shipOwner = findShipOwnerByName(name);
        if (shipOwner == null) return NS;
        return "Ship Owner <<" + shipOwner.getName() + ">> \n" +
                " " + shipOwner.getDescription() + "\n" +
                "Country of registration: "
                + shipOwner.getCountry().getName();
    }

    @Override
    public String shipOwnerVessels(String shipOwnerName) {
        StringBuilder stringBuilder = new StringBuilder();
        findShipOwnerByName(shipOwnerName).getVessels().forEach(vessel ->
                stringBuilder.append(vessel).append("\n"));
        return String.valueOf(stringBuilder);
    }

    @Override
    public String addNewShipOwner(String countryName, String shipOwnerName, String description) {
        Country country = findCountryByName(countryName);
        if (country == null) {
            shipOwnerRepository.save(new ShipOwner(shipOwnerName, description,
                    countryRepository.save(new Country(countryName))));
            return "Country: <<" + countryName + ">> and Ship Owner: <<" +
                    shipOwnerName + ">> have been added to bases";
        } else if (findShipOwnerByName(shipOwnerName) == null) {
            shipOwnerRepository.save(new ShipOwner(shipOwnerName, description, country));
            return "Ship Owner: <<" + shipOwnerName + ">> is added to service";
        } else return "This Ship Owner is already added";
    }

    @Override
    public String refactorCountryForShipOwner(String shipOwnerName, String oldCountryName) {
        Country country = findCountryByName(oldCountryName);
        ShipOwner foundedShipOwner = findShipOwnerByName(shipOwnerName);
        if (foundedShipOwner == null) {
            return NS;
        } else if (country != null) {
            shipOwnerRepository.save(
                    Stream.of(foundedShipOwner).toList().stream().peek(shipOwner -> {
                        shipOwner.setCountry(country);
                        shipOwner.getVessels().forEach(vessel -> vessel.setCountry(country));
                    }).toList().stream().findAny().orElse(foundedShipOwner));
            return "Ship Owner updated his country";
        }
        return NC;
    }

    @Override
    public String setNameForShipOwner(String oldShipOwnerName, String newShipOwnerName) {
        ShipOwner foundedShipOwner = findShipOwnerByName(oldShipOwnerName);
        if (foundedShipOwner != null) {
            shipOwnerRepository.save(
                    Stream.of(foundedShipOwner).toList().stream().peek(
                                    shipOwner -> shipOwner.setName(newShipOwnerName))
                            .toList().stream().findAny().orElse(foundedShipOwner));
            return "Ship Owner have been refactored his name";
        } else {
            return NS;
        }
    }

    @Override
    public String removeFromBaseShipOwner(String shipOwnerName) {
        try {
            shipOwnerRepository.delete(findShipOwnerByName(shipOwnerName));
            return "Ship Owner has been deleted";
        } catch (NullPointerException e) {
            return NS;
        }
    }
}