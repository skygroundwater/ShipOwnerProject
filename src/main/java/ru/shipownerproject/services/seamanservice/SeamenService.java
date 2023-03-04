package ru.shipownerproject.services.seamanservice;

import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

public interface SeamenService {

    List<Country> allCountries();

    List<Vessel> allVessels();

    Seaman seaman(Long id);

    String showInfoAboutSeaman(String fullName);

    String addNewSeamanToBase(String fullName, String passport, String position, String birthDate,
                              String birthPlace, String citizenship, String IMO);

    void addNewSeamanToBase(Seaman seaman);

    void removeSeamanFromBase(Long id);

    String removeSeamanFromBaseByPassport(String passport);

    void refactorSeamanInBase(Seaman seaman, Long id);

    String refactorSeamanInBase(String passport, String newFullName, String newPosition, String newBirthDate,
                                String newBirthPlace, String newCitizenship);
}
