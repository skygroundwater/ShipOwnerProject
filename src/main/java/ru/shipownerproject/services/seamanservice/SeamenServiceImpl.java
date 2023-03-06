package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.seamandatabase.SeamanRepository;
import ru.shipownerproject.databases.vesselrepository.VesselRepository;
import ru.shipownerproject.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.seaman.passport.SeamanPassport;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;
import static ru.shipownerproject.services.vesselservice.VesselsServiceImpl.NV;

@Service
public class SeamenServiceImpl implements SeamenService {

    private final SeamanRepository seamanRepository;

    private final CountryRepository countryRepository;

    private final VesselRepository vesselRepository;

    private static final String NSM = "That seaman is not founded in base.";

    public SeamenServiceImpl(SeamanRepository seamanRepository, CountryRepository countryRepository, VesselRepository vesselRepository) {
        this.seamanRepository = seamanRepository;
        this.countryRepository = countryRepository;
        this.vesselRepository = vesselRepository;
    }

    private Vessel findVesselByIMO(Seaman seaman){
        return vesselRepository.findByIMO(seaman.getVessel().getIMO())
                .stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    private Seaman findSeamanByPassportNumber(String passport){
        return seamanRepository.findAll().stream().filter(
                seaman -> seaman.getSeamanPassport().getPassport()
                        .equals(passport)).findAny().orElse(null);
    }

    private Country findCountryByCitizenship(Seaman seaman){
        return countryRepository.findByName(seaman.getCitizenship().getName()).stream()
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private Seaman findById(Long id){
        return seamanRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NSM));
    }

    @Override
    public Seaman showInfoAboutSeaman(Long id) {
        return findById(id);
    }

    @Override
    public void addNewSeamanToBase(Seaman seaman) {
        seamanRepository.save(new Seaman(seaman.getFullName(), seaman.getPosition(),
                findVesselByIMO(seaman), findCountryByCitizenship(seaman), seaman.getBirth(),
                seaman.getBirthPlace(), findVesselByIMO(seaman).getShipOwner(), seaman.getSeamanPassport().getPassport()));
    }

    @Override
    public void removeSeamanFromBase(Long id){
        seamanRepository.delete(findById(id));
    }

    @Override
    public void refactorSeamanInBase(Long id, Seaman seaman){
        seamanRepository.save(Stream.of(findById(id)).peek(s -> {
            s.setFullName(seaman.getFullName());
            s.setBirth(seaman.getBirth());
            s.setShipowner(findVesselByIMO(seaman).getShipOwner());
            s.setBirthPlace(seaman.getBirthPlace());
            s.setPosition(seaman.getPosition());
            s.setCitizenship(findCountryByCitizenship(seaman));
            s.setVessel(findVesselByIMO(seaman));
        }).findAny().get());
    }
}