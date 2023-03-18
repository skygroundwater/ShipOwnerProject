package ru.shipownerproject.services.seamanservice;

import ru.shipownerproject.models.seaman.Seaman;

public interface SeamenService {

    Seaman showInfoAboutSeaman(Integer passportNumber);

    void addNewSeamanToBase(Seaman seaman);

    void removeSeamanFromBase(Integer passportNumber);

    void refactorSeamanInBase(Seaman seaman);
}
