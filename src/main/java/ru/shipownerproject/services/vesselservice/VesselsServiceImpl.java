package ru.shipownerproject.services.vesselservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.vesselrepository.VesselsRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.countries.ports.Port;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.models.vessels.type.VesselType;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.services.countryservice.portservice.PortsService;
import ru.shipownerproject.services.shipsownerservice.ShipOwnersService;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.shipownerproject.models.vessels.type.VesselType.NVT;
import static ru.shipownerproject.utils.exceptions.ErrorResponse.whatIfEmpty;

@Service
public class VesselsServiceImpl implements VesselsService {

    private final VesselsRepository vesselsRepository;
    private final ShipOwnersService shipOwnersService;
    private final CountriesService countriesService;
    private final PortsService portsService;

    public VesselsServiceImpl(VesselsRepository vesselsRepository,
                              ShipOwnersService shipOwnersService,
                              CountriesService countriesService,
                              PortsService portsService) {
        this.vesselsRepository = vesselsRepository;
        this.shipOwnersService = shipOwnersService;
        this.countriesService = countriesService;
        this.portsService = portsService;
    }

    public static final String NV = "This vessel is not available.";

    public static final String SAME_VESSEL = "Vessel with same IMO number ";

    private Vessel findById(Long id) {
        return vesselsRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    private void checkVesselByIMO(Vessel vessel){
        if(vesselsRepository.findByIMO(vessel.getIMO()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(SAME_VESSEL);
    }

    private Country findCountryByName(Vessel vessel){
        return countriesService.findCountryByName(vessel.getCountry().getName());
    }

    private ShipOwner findShipOwnerByName(Vessel vessel){
        return shipOwnersService.findShipOwnerByName(vessel.getShipOwner().getName());
    }

    private Port findPortByName(Vessel vessel){
        return portsService.findPortByName(vessel.getPort().getName());
    }

    private VesselType findTypeByName(Vessel vessel) {
        return Arrays.stream(VesselType.values()).filter(vesselType ->
                        vesselType.getType().equals(vessel.getVesselType().getType()))
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NVT));
    }

    @Override
    public Vessel findVesselByIMO(Integer IMO){
        return vesselsRepository.findByIMO(IMO).stream().findAny()
                .orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    @Override
    public Vessel vessel(Long id) {
        return findById(id);
    }

    @Override
    public void addNewVessel(Vessel vessel) {
        checkVesselByIMO(vessel);
        vesselsRepository.save(new Vessel(vessel.getName(),
                vessel.getIMO(), findShipOwnerByName(vessel),
                findTypeByName(vessel), findCountryByName(vessel),
                findPortByName(vessel),  vessel.getDateOfBuild()));
    }

    @Override
    public void removeVesselFromBase(Long id) {
        vesselsRepository.delete(findById(id));
    }

    @Override
    public List<Seaman> getInfoAboutCrew(Long id) {
        return (List<Seaman>) whatIfEmpty(findById(id).getSeamen(), "that vessel's crew");
    }

    @Override
    public Port getPortOfRegistration(Long id){
        return findById(id).getPort();
    }

    @Override
    public ShipOwner getVesselShipOwner(Long id){
        return findById(id).getShipOwner();
    }

    @Override
    public Country getCountryOfRegistration(Long id){
        return findById(id).getCountry();
    }

    @Override
    public void refactorVesselInBase(Long id, Vessel vessel) {
        vesselsRepository.save(Stream.of(findById(id)).peek(v -> {
            v.setName(vessel.getName());
            v.setCountry(findCountryByName(vessel));
            v.setShipOwner(findShipOwnerByName(vessel));
            v.setVesselType(vessel.getVesselType());
            v.setPort(findPortByName(vessel));
            v.setDateOfBuild(vessel.getDateOfBuild());
        }).findAny().get());
    }

    @Override
    public List<Vessel> allVesselsByType(String type) {
        return vesselsRepository.findAll().stream().filter(vessel ->
                vessel.getVesselType().getType().equals(type)).collect(Collectors.toList());
    }
}