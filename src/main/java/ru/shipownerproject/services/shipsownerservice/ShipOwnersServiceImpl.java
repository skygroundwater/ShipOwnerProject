package ru.shipownerproject.services.shipsownerservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnersRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.ListIsEmptyException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ShipOwnersServiceImpl implements ShipOwnersService {

    private final ShipOwnersRepository shipOwnersRepository;
    private final CountriesService countriesService;

    public ShipOwnersServiceImpl(ShipOwnersRepository shipOwnersRepository, CountriesService countriesService) {
        this.shipOwnersRepository = shipOwnersRepository;
        this.countriesService = countriesService;
    }

    public static final String NS = "This Ship Owner is not available.";

    public static final String SAME_SHIPOWNER = "Ship owner with same name ";

    private Country findCountryByName(ShipOwner shipOwner) {
        return countriesService.findCountryByName(shipOwner.getCountry().getName());
    }

    private void checkShipOwnerIfPresent(ShipOwner shipOwner) {
        if (shipOwnersRepository.findByNameWithVessels(shipOwner.getName()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(SAME_SHIPOWNER);
    }

    @Override
    public ShipOwner findShipOwnerByName(String name){
        return shipOwnersRepository.findShipOwnerByName(name).stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NS));
    }

    @Override
    public ShipOwner findShipOwnerByNameWithVessels(String name) {
        return shipOwnersRepository.findByNameWithVessels(name).stream().findAny()
                .orElseThrow(() -> new ListIsEmptyException("that ship owner's vessels"));
    }

    @Override
    public ShipOwner findShipOwnerByNameWithSeamen(String name){
        return shipOwnersRepository.findShipOwnerByNameWithSeamen(name).stream().findAny().orElseThrow(() -> new ListIsEmptyException("that ship owner's seamen"));
    }

    @Override
    public List<Vessel> shipOwnerVessels(String name) {
        return findShipOwnerByNameWithVessels(name).getVessels();
    }

    @Override
    public List<Seaman> shipOwnerSeamen(String name) {
        return findShipOwnerByNameWithSeamen(name).getSeamen();
    }

    @Override
    public void addNewShipOwner(ShipOwner shipOwner) {
        checkShipOwnerIfPresent(shipOwner);
        shipOwnersRepository.save(new ShipOwner(shipOwner.getName(),
                shipOwner.getDescription(), findCountryByName(shipOwner)));
    }

    @Override
    public void refactorShipOwner(ShipOwner shipOwner) {
        shipOwnersRepository.save(Stream.of(findShipOwnerByName(shipOwner.getName())).peek(sw -> {
            sw.setName(shipOwner.getName());
            sw.setCountry(findCountryByName(shipOwner));
            sw.setDescription(shipOwner.getDescription());
        }).findAny().get());
    }

    @Override
    public void removeFromBaseShipOwner(String name) {
        shipOwnersRepository.delete(findShipOwnerByName(name));
    }
}