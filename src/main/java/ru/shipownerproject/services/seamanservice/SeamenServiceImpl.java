package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.databases.seamandatabase.SeamenRepository;
import ru.shipownerproject.databases.vesselrepository.VesselsRepository;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.stream.Stream;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;
import static ru.shipownerproject.services.vesselservice.VesselsServiceImpl.NV;

@Service
public class SeamenServiceImpl implements SeamenService {

    private final SeamenRepository seamenRepository;

    private final CountriesRepository countriesRepository;

    private final VesselsRepository vesselsRepository;

    private static final String NSM = "That seaman is not founded in base.";

    public SeamenServiceImpl(SeamenRepository seamenRepository, CountriesRepository countriesRepository, VesselsRepository vesselsRepository) {
        this.seamenRepository = seamenRepository;
        this.countriesRepository = countriesRepository;
        this.vesselsRepository = vesselsRepository;
    }

    private Vessel findVesselByIMO(Seaman seaman){
        return vesselsRepository.findByIMO(seaman.getVessel().getIMO())
                .stream().findAny().orElseThrow(() -> new NotFoundInBaseException(NV));
    }

    private Seaman findSeamanByPassportNumber(String passport){
        return seamenRepository.findAll().stream().filter(
                seaman -> seaman.getSeamanPassport().getPassport()
                        .equals(passport)).findAny().orElse(null);
    }

    private Country findCountryByCitizenship(Seaman seaman){
        return countriesRepository.findByName(seaman.getCitizenship().getName()).stream()
                .findAny().orElseThrow(() -> new NotFoundInBaseException(NC));
    }

    private Seaman findById(Long id){
        return seamenRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NSM));
    }

    @Override
    public Seaman showInfoAboutSeaman(Long id) {
        return findById(id);
    }

    @Override
    public void addNewSeamanToBase(Seaman seaman) {
        seamenRepository.save(new Seaman(seaman.getFullName(), seaman.getPosition(),
                findVesselByIMO(seaman), findCountryByCitizenship(seaman), seaman.getBirth(),
                seaman.getBirthPlace(), findVesselByIMO(seaman).getShipOwner(), seaman.getSeamanPassport().getPassport()));
    }

    @Override
    public void removeSeamanFromBase(Long id){
        seamenRepository.delete(findById(id));
    }

    @Override
    public void refactorSeamanInBase(Long id, Seaman seaman){
        seamenRepository.save(Stream.of(findById(id)).peek(s -> {
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