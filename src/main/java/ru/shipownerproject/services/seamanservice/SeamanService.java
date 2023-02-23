package ru.shipownerproject.services.seamanservice;

import org.springframework.stereotype.Service;


public interface SeamanService {


    String showInfoAboutSeaman(String fullName);

    String addNewSeamanToBase(String fullName, String position, String birthDate,
                              String birthPlace, String citizenship, String IMO);
}
