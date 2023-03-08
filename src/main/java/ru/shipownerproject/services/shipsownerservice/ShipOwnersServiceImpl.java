package ru.shipownerproject.services.shipsownerservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnersRepository;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;
import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;

@Service
public class ShipOwnersServiceImpl implements ShipOwnersService {

    private final ShipOwnersRepository shipOwnersRepository;
    private final CountriesRepository countriesRepository;

    public ShipOwnersServiceImpl(ShipOwnersRepository shipOwnersRepository, CountriesRepository countriesRepository) {
        this.shipOwnersRepository = shipOwnersRepository;
        this.countriesRepository = countriesRepository;
    }

    public static final String NS = "This Ship Owner is not available.";

    public static final String SAME_SHIPOWNER = "Ship owner with same name ";

    private Country findCountryByName(ShipOwner shipOwner){
        return countriesRepository.findByName(shipOwner.getCountry().getName())
                .stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private ShipOwner findById(Long id){
        return shipOwnersRepository.findById(id).orElseThrow(()
                -> new NotFoundInBaseException(NS));
    }

    private void checkShipOwnerByName(ShipOwner shipOwner){
        if(shipOwnersRepository.findByName(shipOwner.getName()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(SAME_SHIPOWNER);
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
        checkShipOwnerByName(shipOwner);
        shipOwnersRepository.save(new ShipOwner(shipOwner.getName(),
                shipOwner.getDescription(), findCountryByName(shipOwner)));
    }

    @Override
    public void refactorShipOwner(Long id, ShipOwner shipOwner) {
        checkShipOwnerByName(shipOwner);
        shipOwnersRepository.save(Stream.of(findById(id)).peek(sw -> {
            sw.setName(shipOwner.getName());
            sw.setCountry(findCountryByName(shipOwner));
            sw.setDescription(shipOwner.getDescription());
        }).findAny().get());
    }

    @Override
    public void removeFromBaseShipOwner(Long id) {
        shipOwnersRepository.delete(findById(id));
    }
}