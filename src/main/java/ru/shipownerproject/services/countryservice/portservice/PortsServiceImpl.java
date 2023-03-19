package ru.shipownerproject.services.countryservice.portservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.portsdatabase.PortsRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.List;
import java.util.stream.Stream;

import static ru.shipownerproject.utils.exceptions.ErrorResponse.whatIfEmpty;

@Service
public class PortsServiceImpl implements PortsService {

    private final PortsRepository portsRepository;

    private final CountriesService countriesService;

    public static final String NP = "This port is not available.";

    public static final String SAME_PORT = "Port with the same name ";

    public PortsServiceImpl(PortsRepository portsRepository, CountriesService countriesService) {
        this.portsRepository = portsRepository;
        this.countriesService = countriesService;
    }

    private void checkPortInDB(Port port) {
        if (portsRepository.findByName(port.getName()).stream().findAny()
                .isPresent()) throw new AlreadyAddedToBaseException(SAME_PORT);
    }

    private Country findCountryByName(Port port) {
        return countriesService.findCountryByName(port.getCountry().getName());
    }

    @Override
    public Port findPortByName(String name) {
        return portsRepository.findByName(name).stream().findAny()
                .orElseThrow(() -> new NotFoundInBaseException(NP));
    }

    @Override
    public void addNewPort(Port port) {
        checkPortInDB(port);
        portsRepository.save(new Port(port.getName(),
                findCountryByName(port), port.getNav_description()));
    }

    @Override
    public void refactorPort(Port port) {
        portsRepository.save(
                Stream.of(findPortByName(port.getName())).peek(p -> {
                    p.setCountry(findCountryByName(port));
                    p.setNav_description(port.getNav_description());
                    p.setName(port.getName());
                }).findAny().get()
        );
    }

    @Override
    public List<Vessel> vesselRegisteredThisPort(String name){
        return (List<Vessel>) whatIfEmpty(findPortByName(name).getRegVessels(), "that port's vessels");
    }

    @Override
    public void deletePortFromDB(String name) {
        portsRepository.delete(findPortByName(name));
    }
}
