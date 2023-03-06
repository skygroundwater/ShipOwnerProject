package ru.shipownerproject.services.vesselservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.databases.vesselrepository.VesselRepository;
import ru.shipownerproject.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.shipownerproject.models.vessels.type.VesselType.NVT;
import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;
import static ru.shipownerproject.services.shipsownerservice.ShipOwnersServiceImpl.NS;

@Service
public class VesselsServiceImpl implements VesselsService {

    private final VesselRepository vesselRepository;
    private final ShipOwnerRepository shipOwnerRepository;
    private final CountryRepository countryRepository;

    public VesselsServiceImpl(VesselRepository vesselRepository,
                              ShipOwnerRepository shipOwnerRepository,
                              CountryRepository countryRepository) {
        this.vesselRepository = vesselRepository;
        this.shipOwnerRepository = shipOwnerRepository;
        this.countryRepository = countryRepository;
    }

    public static final String NV = "This vessel is not available. ";

    private Vessel findVesselByImo(Integer IMO) {
        return vesselRepository.findByIMO(IMO).stream().findAny().orElse(null);
    }

    private Country findCountryByName(Vessel vessel) {
        return countryRepository.findByName(vessel.getCountry().getName()).stream()
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private ShipOwner findShipOwnerByName(Vessel vessel) {
        return shipOwnerRepository.findByName(vessel.getShipOwner().getName())
                .stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NS));
    }

    private Vessel findById(Long id) {
        return vesselRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    private VesselType findByTypeName(Vessel vessel) {
        return Arrays.stream(VesselType.values()).filter(vesselType ->
                        vesselType.getType().equals(vessel.getVesselType().getType()))
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NVT));
    }

    @Override
    public Vessel vessel(Long id) {
        return findById(id);
    }

    @Override
    public void addNewVessel(Vessel vessel, Integer IMO) {
        if (findVesselByImo(IMO) != null) throw new AlreadyAddedToBaseException("Vessel with same IMO number ");
        vesselRepository.save(new Vessel(vessel.getName(), IMO, findShipOwnerByName(vessel),
                findByTypeName(vessel), findCountryByName(vessel), vessel.getDateOfBuild()));
    }

    @Override
    public void removeVesselFromBase(Long id) {
        vesselRepository.delete(findById(id));
    }

    @Override
    public List<Seaman> getInfoAboutCrew(Long id) {
        return findById(id).getSeamen();
    }

    @Override
    public void refactorVesselInBase(Long id, Vessel vessel) {
        if (findVesselByImo(vessel.getIMO()) == null) throw new NotFoundInBaseException(NV);
        vesselRepository.save(Stream.of(findById(id)).peek(v -> {
            v.setName(vessel.getName());
            v.setIMO(vessel.getIMO());
            v.setCountry(findCountryByName(vessel));
            v.setShipOwner(findShipOwnerByName(vessel));
            v.setVesselType(vessel.getVesselType());
            v.setDateOfBuild(vessel.getDateOfBuild());
        }).findAny().get());
    }

    @Override
    public List<Vessel> allVesselsByType(String type) {
        return vesselRepository.findAll().stream().filter(vessel ->
                vessel.getVesselType().getType().equals(type)).collect(Collectors.toList());
    }
}