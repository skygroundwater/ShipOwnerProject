package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.seamandatabase.SeamanRepository;
import ru.shipownerproject.databases.vesselrepository.VesselRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;
import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;

@Service
public class SeamenServiceImpl implements SeamenService {

    private final SeamanRepository seamanRepository;

    private final CountryRepository countryRepository;

    private final VesselRepository vesselRepository;

    private static final String NSM = "That seaman is not founded in base";

    public SeamenServiceImpl(SeamanRepository seamanRepository, CountryRepository countryRepository, VesselRepository vesselRepository) {
        this.seamanRepository = seamanRepository;
        this.countryRepository = countryRepository;
        this.vesselRepository = vesselRepository;
    }

    private Vessel findVesselByIMO(String IMO){
        return vesselRepository.findByIMO(IMO)
                .stream().findAny().orElse(null);
    }

    private Seaman findSeamanByPassportNumber(String passport){
        return seamanRepository.findAll().stream().filter(
                seaman -> seaman.getSeamanPassport().getPassport()
                        .equals(passport)).findAny().orElse(null);
    }

    private Country findCountryByCitizenship(String countryName){
        return countryRepository.findByName(countryName).stream()
                .findAny().orElse(null);
    }
    private Seaman findById(Long id){
        return seamanRepository.findById(id).get();
    }

    @Override
    public List<Country> allCountries(){
        return countryRepository.findAll();
    }

    @Override
    public List<Vessel> allVessels(){
        return vesselRepository.findAll();
    }

    @Override
    public Seaman seaman(Long id){
        return seamanRepository.findById(id).get();
    }

    @Override
    public String showInfoAboutSeaman(String passport) {
        Seaman seaman = findSeamanByPassportNumber(passport);
        if (seaman != null) {
            return seaman.getInfo();
        } else {
            return NSM;
        }
    }

    @Override
    public String addNewSeamanToBase(String fullName, String passport, String position, String birthDate,
                                     String birthPlace, String citizenship, String IMO) {
        Country country = findCountryByCitizenship(citizenship);
        Vessel vessel = findVesselByIMO(IMO);
        if (findSeamanByPassportNumber(passport) != null) return "That seaman is already in base";
        if (country != null && vessel != null) {
            seamanRepository.save(new Seaman(fullName, position, vessel, country,
                    birthDate, birthPlace, vessel.getShipOwner(), passport));
            return "Seaman has been added to base";
        }
        return "Database has no country or vessel data";
    }

    @Override
    public void addNewSeamanToBase(Seaman seaman){
        seaman.setShipowner(seaman.getVessel().getShipOwner());
        seamanRepository.save(seaman);
    }

    @Override
    public void removeSeamanFromBase(Long id){
        seamanRepository.deleteById(id);
    }

    @Override
    public String removeSeamanFromBaseByPassport(String passport){
        Seaman foundedSeaman = findSeamanByPassportNumber(passport);
        if(foundedSeaman == null) return NSM;
        seamanRepository.delete(foundedSeaman);
        return "Seaman has been deleted";
    }

    @Override
    public void refactorSeamanInBase(Seaman seaman, Long id){
        seamanRepository.save(Stream.of(findById(id)).peek(s -> {
            s.setFullName(seaman.getFullName());
            s.setVessel(seaman.getVessel());
            s.setShipowner(seaman.getVessel().getShipOwner());
            s.setPosition(seaman.getPosition());
            s.setBirth(seaman.getBirth());
            s.setBirthPlace(seaman.getBirthPlace());
        }).findAny().get());

    }

    @Override
    public String refactorSeamanInBase(String passport,String newFullName, String newPosition, String newBirthDate,
                                       String newBirthPlace, String newCitizenship){
        Seaman foundedSeaman = findSeamanByPassportNumber(passport);
        Country citizenship = findCountryByCitizenship(newCitizenship);
        if(citizenship == null) return NC;
        if (foundedSeaman == null) return NSM;
        seamanRepository.save(Stream.of(foundedSeaman).toList()
                .stream().peek(seaman ->
                {seaman.setFullName(newFullName);
                    seaman.setPosition(newPosition);
                    seaman.setCitizenship(citizenship);
                    seaman.setBirth(newBirthDate);
                    seaman.setBirthPlace(newBirthPlace);})
                        .toList().stream().findAny().orElse(foundedSeaman));
        return "That seaman had been refactored";
    }
}