package ru.shipownerproject.services.vesselservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.repositories.VesselsRepository;
import ru.shipownerproject.models.Country;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.models.Seaman;
import ru.shipownerproject.models.ShipOwner;
import ru.shipownerproject.models.Vessel;
import ru.shipownerproject.models.enums.VesselType;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.services.portservice.PortsService;
import ru.shipownerproject.services.shipsownerservice.ShipOwnersService;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ru.shipownerproject.models.enums.VesselType.NVT;
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

    private void checkVesselByIMO(Vessel vessel) {
        if (vesselsRepository.findByIMO(vessel.getIMO()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(SAME_VESSEL);
    }

    private Country findCountryByName(Vessel vessel) {
        return countriesService.findCountryByName(vessel.getCountry().getName());
    }

    private ShipOwner findShipOwnerByName(Vessel vessel) {
        return shipOwnersService.findShipOwnerByNameWithSeamen(vessel.getShipOwner().getName());
    }

    private Port findPortByName(Vessel vessel) {
        return portsService.findPortByName(vessel.getPort().getName());
    }

    private VesselType findTypeByName(Vessel vessel) {
        return Arrays.stream(VesselType.values()).filter(vesselType ->
                        vesselType.getType().equals(vessel.getVesselType().getType()))
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NVT));
    }

    @Override
    public Vessel findVesselByIMO(Integer IMO) {
        return vesselsRepository.findByIMO(IMO).stream().findAny()
                .orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    @Override
    public void addNewVessel(Vessel vessel) {
        checkVesselByIMO(vessel);
        vesselsRepository.save(new Vessel(vessel.getName(),
                vessel.getIMO(), findShipOwnerByName(vessel),
                findTypeByName(vessel), findCountryByName(vessel),
                findPortByName(vessel), vessel.getDateOfBuild()));
    }

    @Override
    public void removeVesselFromBase(Integer IMO) {
        vesselsRepository.delete(findVesselByIMO(IMO));
    }

    @Override
    public List<Seaman> getInfoAboutCrew(Integer IMO) {
        return (List<Seaman>) whatIfEmpty(findVesselByIMO(IMO).getSeamen(),
                "that vessel's crew");
    }

    @Override
    public Port getPortOfRegistration(Integer IMO) {
        return findVesselByIMO(IMO).getPort();
    }

    @Override
    public ShipOwner getVesselShipOwner(Integer IMO) {
        return findVesselByIMO(IMO).getShipOwner();
    }

    @Override
    public Country getCountryOfRegistration(Integer IMO) {
        return findVesselByIMO(IMO).getCountry();
    }

    @Override
    public void refactorVesselInBase(Vessel vessel) {
        vesselsRepository.save(Stream.of(findVesselByIMO(vessel.getIMO())).peek(v -> {
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
        return (List<Vessel>) whatIfEmpty(vesselsRepository.findVesselByVesselType(
                        Arrays.stream(VesselType.values())
                                .filter(vt -> vt.getType().equals(type)).findAny()
                                .orElseThrow(() -> new NotFoundInBaseException(NVT))),
                "that type of vessels");
    }
}