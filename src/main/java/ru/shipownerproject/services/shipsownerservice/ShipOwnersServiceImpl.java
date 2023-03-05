package ru.shipownerproject.services.shipsownerservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.exceptions.NotFoundInBaseException;
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

    public static final String NS = "This Ship Owner is not available.";

    private Country findCountryByName(ShipOwner shipOwner){
        return countryRepository.findByName(shipOwner.getCountry().getName())
                .stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private ShipOwner findById(Long id){
        return shipOwnerRepository.findById(id).orElseThrow(()
                -> new NotFoundInBaseException(NS));
    }

    private ShipOwner findByName(ShipOwner shipOwner){
        return shipOwnerRepository.findByName(shipOwner.getName())
                .stream().findAny().orElse(null);
    }

    @Override
    public ShipOwner shipOwner(Long id) {
        return findById(id);
    }

    @Override
    public List<Vessel> shipOwnerVessels(Long id) {
        return findById(id).getVessels();
    }

    @Override
    public void addNewShipOwner(ShipOwner shipOwner) {
        if(findByName(shipOwner) != null) throw  new AlreadyAddedToBaseException("That shipowner ");
        shipOwnerRepository.save(new ShipOwner(shipOwner.getName(),
                shipOwner.getDescription(), findCountryByName(shipOwner)));
    }

    @Override
    public void refactorShipOwner(Long id, ShipOwner shipOwner) {
        if(findByName(shipOwner) != null) throw new AlreadyAddedToBaseException("Ship owner with same name ");
        shipOwnerRepository.save(Stream.of(findById(id)).peek(sw -> {
            sw.setName(shipOwner.getName());
            sw.setCountry(findCountryByName(shipOwner));
            sw.setDescription(shipOwner.getDescription());
        }).findAny().get());
    }

    @Override
    public void removeFromBaseShipOwner(Long id) {
        shipOwnerRepository.delete(findById(id));
    }
}