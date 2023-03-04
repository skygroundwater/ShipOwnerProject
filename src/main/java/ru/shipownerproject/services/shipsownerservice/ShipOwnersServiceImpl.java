package ru.shipownerproject.services.shipsownerservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;
import java.util.stream.Stream;

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

    private ShipOwner findById(Long id) {
        return shipOwnerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<ShipOwner> allShipOwners() {
        return shipOwnerRepository.findAll();
    }

    @Override
    public ShipOwner shipOwner(Long id) {
        return findById(id);
    }

    @Override
    public List<Seaman> shipOwnerSeamen(Long id) {
        return findById(id).getSeamen();
    }

    @Override
    public List<Vessel> shipOwnerVessels(Long id) {
        return findById(id).getVessels();
    }

    @Override
    public void addNewShipOwner(ShipOwner shipOwner) {
        shipOwnerRepository.save(shipOwner);
    }

    @Override
    public void refactorShipOwner(ShipOwner shipOwner, Long id) {
        ShipOwner refShipOwner = findById(id);
        refShipOwner.setName(shipOwner.getName());
        refShipOwner.setCountry(shipOwner.getCountry());
        refShipOwner.setDescription(shipOwner.getDescription());

        shipOwnerRepository.save(refShipOwner);
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
    public void removeFromBaseShipOwner(Long id) {
        shipOwnerRepository.deleteById(id);
    }
}