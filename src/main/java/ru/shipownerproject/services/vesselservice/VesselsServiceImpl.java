package ru.shipownerproject.services.vesselservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.databases.vesselrepository.VesselRepository;
import ru.shipownerproject.databases.vesselrepository.vesseltyperepository.VesselTypeRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;
import static ru.shipownerproject.services.shipsownerservice.ShipOwnersServiceImpl.NS;
import static ru.shipownerproject.services.vesselservice.typeservice.VesselTypesServiceImpl.NVT;

@Service
public class VesselsServiceImpl implements VesselsService {

    private final VesselRepository vesselRepository;
    private final ShipOwnerRepository shipOwnerRepository;
    private final VesselTypeRepository vesselTypeRepository;
    private final CountryRepository countryRepository;

    public VesselsServiceImpl(VesselRepository vesselRepository,
                              ShipOwnerRepository shipOwnerRepository,
                              VesselTypeRepository vesselTypeRepository,
                              CountryRepository countryRepository) {
        this.vesselRepository = vesselRepository;
        this.shipOwnerRepository = shipOwnerRepository;
        this.vesselTypeRepository = vesselTypeRepository;
        this.countryRepository = countryRepository;
    }

    public static final String NV = "This vessel is not available";

    private Vessel findVesselByImo(String IMO) {
        return vesselRepository.findByIMO(IMO).stream().findAny().orElse(null);
    }

    private Country findCountryByName(String countryName) {
        return countryRepository.findByName(countryName).stream().findAny().orElse(null);
    }

    private ShipOwner findShipOwnerByName(String shipOwnerName) {
        return shipOwnerRepository.findByName(shipOwnerName).stream().findAny().orElse(null);
    }

    private Vessel findById(Long id){
        return vesselRepository.findById(id).stream().findAny().orElse(null);
    }
    private VesselType findTypeById(Byte typeId) {
        return vesselTypeRepository.findById(typeId).orElse(null);
    }

    @Override
    public List<Country> allCountries(){
        return countryRepository.findAll();
    }
    @Override
    public List<ShipOwner> allShipOwners(){
        return shipOwnerRepository.findAll();
    }
    @Override
    public List<Vessel> allVessels(){
        return vesselRepository.findAll();
    }

    @Override
    public Vessel vessel(Long id){
        return vesselRepository.findById(id).get();
    }

    @Override
    public String vessel(String IMO) {
        Vessel vessel = findVesselByImo(IMO);
        if (vessel == null) return NV;
        else return vessel.toString();
    }

    @Override
    public void addNewVessel(Vessel vessel){
        vesselRepository.save(vessel);
    }

    @Override
    public String addNewVessel(String vesselName, String countryName, String shipOwnerName,
                               String IMO, Byte typeId) {
        ShipOwner shipOwner = findShipOwnerByName(shipOwnerName);
        Vessel vessel = findVesselByImo(IMO);
        VesselType vesselType = findTypeById(typeId);
        Country country = findCountryByName(countryName);
        if (country == null) return NC;
        if (vesselType == null) return NVT;
        if (shipOwner == null) return NS;
        if (vessel != null) return "This vessel is already in base";
        vesselRepository.save(new Vessel(vesselName, IMO, shipOwner, vesselType, country));
        return "New vessel <<" + vesselName + ">> is registered for Ship Owner: " + shipOwner.getName();
    }

    @Override
    public String removeVesselFromBase(String IMO) {
        Vessel vessel = findVesselByImo(IMO);
        if (vessel == null) return NV;
        vesselRepository.delete(vessel);
        return "Vessel have been deleted from base";
    }

    @Override
    public List<Seaman> crew(Long id){
        return vesselRepository.findById(id).get().getSeamen();
    }

    @Override
    public String getInfoAboutCrew(String IMO) {
        Vessel vessel = findVesselByImo(IMO);
        if (vessel == null) return NV;
        StringBuilder stringBuilder = new StringBuilder();
        vessel.getSeamen().forEach(seaman -> stringBuilder.append(seaman).append("\n"));
        return String.valueOf(stringBuilder);
    }

    @Override
    public void removeVesselFromBase(Vessel vessel){
        vesselRepository.delete(vessel);
    }

    @Override
    public void refactorVesselInBase(Vessel vessel, Long id){
        vesselRepository.save(Stream.of(findById(id))
                .toList().stream().peek(v->{
            v.setVesselType(vessel.getVesselType());
            v.setCountry(vessel.getCountry());
            v.setName(vessel.getName());
            v.setShipOwner(vessel.getShipOwner());
        }).findAny().get());
    }

    @Override
    public String refactorVesselInBase(String IMO, String newName, String newCountry,
                                       Byte newVesselTypeId, String newShipOwnerName) {
        Vessel foundedVessel = findVesselByImo(IMO);
        Country country = findCountryByName(newCountry);
        VesselType vesselType = findTypeById(newVesselTypeId);
        ShipOwner shipOwner = findShipOwnerByName(newShipOwnerName);
        if (country == null) return NC;
        if (vesselType == null) return NVT;
        if (shipOwner == null) return NS;
        if (foundedVessel == null) return NV;
        vesselRepository.save(
                Stream.of(foundedVessel).toList().stream().peek(vessel -> {
                    vessel.setCountry(country);
                    vessel.setVesselType(vesselType);
                    vessel.setName(newName);
                    vessel.setShipOwner(shipOwner);})
                        .toList().stream().findAny().orElse(foundedVessel));
                        return "Vessel: <<" + foundedVessel.getName() + ">> has been refactored.";
    }

    @Override
    public String allVesselsByType(Byte id){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            findTypeById(id).getVesselsOfThisType().forEach(
                    vessel -> stringBuilder.append(vessel.toString()));
            return String.valueOf(stringBuilder);
        }catch (NullPointerException e){
            return NVT;
        }
    }
}