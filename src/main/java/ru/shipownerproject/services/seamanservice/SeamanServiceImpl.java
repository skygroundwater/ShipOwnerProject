package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.seamandatabase.SeamanRepository;
import ru.shipownerproject.databases.vesselrepository.VesselRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

@Service
public class SeamanServiceImpl implements SeamanService {

    private final SeamanRepository seamanRepository;

    private final CountryRepository countryRepository;

    private final VesselRepository vesselRepository;

    public SeamanServiceImpl(SeamanRepository seamanRepository, CountryRepository countryRepository, VesselRepository vesselRepository) {
        this.seamanRepository = seamanRepository;
        this.countryRepository = countryRepository;
        this.vesselRepository = vesselRepository;
    }

    @Override
    public String showInfoAboutSeaman(String fullName) {
        Seaman seaman = seamanRepository.findByFullName(fullName)
                .stream().findAny().orElse(null);
        if (seaman != null) {
            return seaman.getInfo();
        } else {
            return "We don't have a seaman with that name";
        }
    }

    @Override
    public String addNewSeamanToBase(String fullName, String position, String birthDate,
                                     String birthPlace, String citizenship, String IMO) {
        Country country = countryRepository.findByName(citizenship)
                .stream().findAny().orElse(null);
        Vessel vessel = vesselRepository.findByIMO(IMO)
                .stream().findAny().orElse(null);
        if (seamanRepository.findByFullName(fullName)
                .stream().findAny().orElse(null) != null) {
            return "That seaman is already in base";
        } else if (country != null && vessel != null) {
            Seaman seaman = new Seaman();
            seaman.setFullName(fullName);
            seaman.setPosition(position);
            seaman.setBirth(birthDate);
            seaman.setBirthPlace(birthPlace);
            seaman.setCitizenship(country);
            seaman.setVessel(vessel);
            seaman.setShipowner(vessel.getShipOwner());
            seamanRepository.save(seaman);
            return "Seaman has been added to base";
        }
        return "Database has no country or vessel data";
    }
}
