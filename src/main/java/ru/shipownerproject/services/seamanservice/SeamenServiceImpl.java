package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.seamandatabase.SeamenRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.services.vesselservice.VesselsService;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.stream.Stream;

@Service
public class SeamenServiceImpl implements SeamenService {

    private final SeamenRepository seamenRepository;

    private final CountriesService countriesService;

    private final VesselsService vesselsService;

    private static final String NSM = "That seaman is not founded in base.";

    public SeamenServiceImpl(SeamenRepository seamenRepository,
                             CountriesService countriesService,
                             VesselsService vesselsService) {
        this.seamenRepository = seamenRepository;
        this.countriesService = countriesService;
        this.vesselsService = vesselsService;
    }

    private Vessel findVesselByIMO(Seaman seaman) {
        return vesselsService.findVesselByIMO(seaman.getVessel().getIMO());
    }

    private Country findCountryByName(Seaman seaman) {
        return countriesService.findCountryByName(seaman.getCitizenship().getName());
    }

    private Seaman findSeamanByPassportNumber(String passport) {
        return seamenRepository.findAll().stream().filter(
                seaman -> seaman.getSeamanPassport().getPassport()
                        .equals(passport)).findAny().orElseThrow(() -> new NotFoundInBaseException(NSM));
    }

    private Seaman findById(Long id) {
        return seamenRepository.findById(id).orElseThrow(() -> new NotFoundInBaseException(NSM));
    }

    @Override
    public Seaman showInfoAboutSeaman(Long id) {
        return findById(id);
    }

    @Override
    public void addNewSeamanToBase(Seaman seaman) {
        seamenRepository.save(new Seaman(seaman.getFullName(), seaman.getPosition(),
                findVesselByIMO(seaman), findCountryByName(seaman),
                seaman.getBirth(), seaman.getBirthPlace(), findVesselByIMO(seaman).getShipOwner(),
                seaman.getSeamanPassport().getPassport()));
    }

    @Override
    public void removeSeamanFromBase(Long id) {
        seamenRepository.delete(findById(id));
    }

    @Override
    public void refactorSeamanInBase(Long id, Seaman seaman) {
        seamenRepository.save(Stream.of(findById(id)).peek(s -> {
            s.setFullName(seaman.getFullName());
            s.setBirth(seaman.getBirth());
            s.setShipowner(findVesselByIMO(seaman).getShipOwner());
            s.setBirthPlace(seaman.getBirthPlace());
            s.setPosition(seaman.getPosition());
            s.setCitizenship(findCountryByName(seaman));
            s.setVessel(findVesselByIMO(seaman));
        }).findAny().get());
    }
}