package ru.shipownerproject.services.countryservice.portservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.databases.countrybase.portsdatabase.PortsRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;

@Service
public class PortsServiceImpl implements PortsService {

    private final PortsRepository portsRepository;

    private final CountriesRepository countriesRepository;

    public static final String NP = "This port is not available.";

    public static final String SAME_PORT = "Port with the same name ";

    public PortsServiceImpl(PortsRepository portsRepository, CountriesRepository countriesRepository) {
        this.portsRepository = portsRepository;
        this.countriesRepository = countriesRepository;
    }

    private void checkPortInDB(Port port) {
        if(portsRepository.findByName(port.getName()).stream().findAny()
                .isPresent()) throw new AlreadyAddedToBaseException(SAME_PORT);
    }

    private Port findById(Integer id) {
        return portsRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NP));
    }

    private Country findCountryByName(Port port){
        return countriesRepository.findByName(port.getCountry().getName()).stream()
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    @Override
    public Port addNewPort(Port port) {
        checkPortInDB(port);
        return portsRepository.save(new Port(port.getName(), findCountryByName(port), port.getNav_description()));
    }

    @Override
    public Port getPortFromDB(Integer id){
        return findById(id);
    }

    @Override
    public Port refactorPort(Integer id, Port port) {
        return portsRepository.save(
                Stream.of(findById(id)).peek(p -> {
                    p.setNav_description(port.getNav_description());
                    p.setName(port.getName());
                }).findAny().get()
        );
    }

    @Override
    public void deletePortFromDB(Integer id){
        portsRepository.delete(findById(id));
    }
}
