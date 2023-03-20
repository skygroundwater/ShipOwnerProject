package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.seamandatabase.SeamenRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.services.vesselservice.VesselsService;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.stream.Stream;

@Service
public class SeamenServiceImpl implements SeamenService {

    private final SeamenRepository seamenRepository;

    private final CountriesService countriesService;

    private final VesselsService vesselsService;

    private static final String NSM = "That seaman is not founded in base.";

    private static final String THAT_SEAMAN = "That seaman ";

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

    private Seaman findSeamanByPassportNumber(Integer passportNumber) {
        return seamenRepository.findByPassportNumber(passportNumber).stream().findAny()
                .orElseThrow(() -> new NotFoundInBaseException(NSM));
    }

    private void checkSeamanInDataBase(Integer passportNumber){
        if(seamenRepository.findByPassportNumber(passportNumber).stream().findAny().isPresent())
            throw new AlreadyAddedToBaseException(THAT_SEAMAN);
    }

    @Override
    public Seaman showInfoAboutSeaman(Integer passportNumber) {
        return findSeamanByPassportNumber(passportNumber);
    }

    @Override
    public void addNewSeamanToBase(Seaman seaman) {
        checkSeamanInDataBase(seaman.getPassportNumber());
        seamenRepository.save(new Seaman(seaman.getFullName(), seaman.getPosition(),
                findVesselByIMO(seaman), findCountryByName(seaman),
                seaman.getBirth(), seaman.getBirthPlace(), findVesselByIMO(seaman).getShipOwner(),
                seaman.getPassportNumber()));
    }

    @Override
    public void removeSeamanFromBase(Integer passportNumber) {
        seamenRepository.delete(findSeamanByPassportNumber(passportNumber));
    }

    @Override
    public void refactorSeamanInBase(Seaman seaman) {
        seamenRepository.save(Stream.of(findSeamanByPassportNumber(seaman.getPassportNumber())).peek(s -> {
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