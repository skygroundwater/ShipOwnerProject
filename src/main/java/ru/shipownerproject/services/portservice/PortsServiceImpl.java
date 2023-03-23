package ru.shipownerproject.services.portservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.repositories.PortsRepository;
import ru.shipownerproject.models.Country;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.models.Vessel;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.ListIsEmptyException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.List;
import java.util.stream.Stream;

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

    private Port findPortByNameWithVessels(String name) {
        return portsRepository.findByNameWithVessels(name).stream().findAny()
                .orElseThrow(() -> new ListIsEmptyException("that port's vessels"));
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
    public List<Vessel> vesselRegisteredThisPort(String name) {
        return findPortByNameWithVessels(name).getRegVessels();
    }

    @Override
    public void deletePortFromDB(String name) {
        portsRepository.delete(findPortByName(name));
    }
}
