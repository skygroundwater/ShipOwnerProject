package ru.shipownerproject.services.seamanservice;

import ru.shipownerproject.models.seaman.Seaman;

public interface SeamenService {


    Seaman showInfoAboutSeaman(Long id);

    void addNewSeamanToBase(Seaman seaman);

    void removeSeamanFromBase(Long id);

    void refactorSeamanInBase(Long id, Seaman seaman);
}
