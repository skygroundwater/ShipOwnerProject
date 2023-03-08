package ru.shipownerproject.services.vesselservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnersRepository;
import ru.shipownerproject.databases.vesselrepository.VesselsRepository;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;
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

    private final VesselsRepository vesselsRepository;
    private final ShipOwnersRepository shipOwnersRepository;
    private final CountriesRepository countriesRepository;

    public VesselsServiceImpl(VesselsRepository vesselsRepository,
                              ShipOwnersRepository shipOwnersRepository,
                              CountriesRepository countriesRepository) {
        this.vesselsRepository = vesselsRepository;
        this.shipOwnersRepository = shipOwnersRepository;
        this.countriesRepository = countriesRepository;
    }

    public static final String NV = "This vessel is not available. ";

    public static final String SAME_VESSEL = "Vessel with same IMO number ";

    private Country findCountryByName(Vessel vessel) {
        return countriesRepository.findByName(vessel.getCountry().getName()).stream()
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private ShipOwner findShipOwnerByName(Vessel vessel) {
        return shipOwnersRepository.findByName(vessel.getShipOwner().getName())
                .stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NS));
    }

    private Vessel findById(Long id) {
        return vesselsRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    private VesselType findByTypeName(Vessel vessel) {
        return Arrays.stream(VesselType.values()).filter(vesselType ->
                        vesselType.getType().equals(vessel.getVesselType().getType()))
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NVT));
    }

    private void checkVesselByIMO(Vessel vessel){
        if(vesselsRepository.findByIMO(vessel.getIMO()).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(SAME_VESSEL);
    }

    @Override
    public Vessel vessel(Long id) {
        return findById(id);
    }

    @Override
    public void addNewVessel(Vessel vessel) {
        checkVesselByIMO(vessel);
        vesselsRepository.save(new Vessel(vessel.getName(), vessel.getIMO(), findShipOwnerByName(vessel),
                findByTypeName(vessel), findCountryByName(vessel), vessel.getDateOfBuild()));
    }

    @Override
    public void removeVesselFromBase(Long id) {
        vesselsRepository.delete(findById(id));
    }

    @Override
    public List<Seaman> getInfoAboutCrew(Long id) {
        return findById(id).getSeamen();
    }

    @Override
    public void refactorVesselInBase(Long id, Vessel vessel) {
        checkVesselByIMO(vessel);
        vesselsRepository.save(Stream.of(findById(id)).peek(v -> {
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
        return vesselsRepository.findAll().stream().filter(vessel ->
                vessel.getVesselType().getType().equals(type)).collect(Collectors.toList());
    }
}