package ru.shipownerproject.services.countryservice.portservice;


import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.portsdatabase.PortsRepository;
import ru.shipownerproject.models.countries.ports.Port;

@Service
public class PortsServiceImpl implements PortsService{

    private final PortsRepository portsRepository;

    public PortsServiceImpl(PortsRepository portsRepository) {
        this.portsRepository = portsRepository;
    }




}
