package ru.shipownerproject.services.shipsownerservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

@Service
public class ShipsOwnerServiceImpl implements ShipsOwnerService {

    private final ShipOwnerRepository shipOwnerRepository;
    private final CountryRepository countryRepository;

    public ShipsOwnerServiceImpl(ShipOwnerRepository shipOwnerRepository, CountryRepository countryRepository) {
        this.shipOwnerRepository = shipOwnerRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public String shipOwner(String name) {
        ShipOwner shipOwner = shipOwnerRepository.findByName(name).stream().findAny().orElse(null);
        if (shipOwner == null) return "This Ship Owner is not available";
        Country country = countryRepository.findById(shipOwner.getCountry().getId()).orElse(null);
        if (country == null) return "Ship Owner cannot to be without country";
        return "Ship Owner <<" + shipOwner.getName() + ">> \n" +
                " " + shipOwner.getDescription() + "\n" +
                "Country of registration: "
                + country.getName();
    }

    @Override
    public String shipOwnerVessels(String shipOwnerName) {
        ShipOwner shipOwner = shipOwnerRepository.findByName(shipOwnerName)
                .stream().findAny().orElse(null);
        StringBuilder stringBuilder = new StringBuilder();
        if (shipOwner != null) {
            for (Vessel vessel : shipOwner.getVessels()) {
                stringBuilder.append(vessel)
                        .append("\n");
            }
            return String.valueOf(stringBuilder);
        }
        return "This Ship Owner is not available";
    }

    @Override
    public String addNewShipOwner(String countryName, String shipOwnerName,
                                  String description) {
        Country country = countryRepository.findByName(countryName)
                .stream().findAny().orElse(null);
        ShipOwner shipOwner = shipOwnerRepository.findByName(shipOwnerName).
                stream().findAny().orElse(null);
        if (shipOwner == null) {
            ShipOwner newShipOwner = new ShipOwner(shipOwnerName, description);
            if (country != null) {
                newShipOwner.setCountry(country);
            } else {
                Country newCountry = new Country(countryName);
                countryRepository.save(newCountry);
                newShipOwner.setCountry(newCountry);
            }
            shipOwnerRepository.save(
                    newShipOwner);
            return "Ship Owner: " + shipOwnerName + " is added to service";
        } else {
            return "This Ship Owner is already added. Choose from list";
        }
    }

    @Override
    public String setCountryForShipOwner(String shipOwnerName, String countryName) {

        Country country = countryRepository.findByName(countryName)
                .stream().findAny().orElse(null);
        ShipOwner shipOwner = shipOwnerRepository.findByName(shipOwnerName).
                stream().findAny().orElse(null);
        if (shipOwner == null) {
            return "This Ship Owner is not in the base. At first please POST it";
        } else if (country != null) {
            shipOwner.setCountry(country);
            shipOwner.getVessels().forEach(vessel -> vessel.setCountry(country));
            shipOwnerRepository.save(shipOwner);
            return "Ship Owner updated his country";
        }
        return "Please add this country to base and repeat request";
    }

    @Override
    public String setNameForShipOwner(String oldShipOwnerName, String newShipOwnerName) {
        ShipOwner shipOwner = shipOwnerRepository.findByName(oldShipOwnerName)
                .stream().findAny().orElse(null);
        if (shipOwner != null) {
            shipOwner.setName(newShipOwnerName);
            shipOwnerRepository.save(shipOwner);
            return "Ship Owner have been refactor his name";
        } else {
            return "We do not have this Ship Owner in the base. At first please add it";
        }
    }

    @Override
    public String removeFromBaseShipOwner(String shipOwnerName) {
        ShipOwner shipOwner = shipOwnerRepository.findByName(shipOwnerName)
                .stream().findAny().orElse(null);
        if (shipOwner != null) {
            shipOwnerRepository.delete(shipOwner);
            return "Ship Owner has been deleted";
        } else {
            return "We do not have that Ship Owner in base";
        }
    }
}