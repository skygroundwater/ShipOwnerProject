package ru.shipownerproject.services.seamanservice;

public interface SeamenService {


    String showInfoAboutSeaman(String fullName);

    String addNewSeamanToBase(String fullName, String passport, String position, String birthDate,
                              String birthPlace, String citizenship, String IMO);

    String removeSeamanFromBaseByPassport(String passport);

    String refactorSeamanInBase(String passport, String newFullName, String newPosition, String newBirthDate,
                                String newBirthPlace, String newCitizenship);
}
