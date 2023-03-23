package ru.shipownerproject.services.countryservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shipownerproject.repositories.CountriesRepository;

@ExtendWith(MockitoExtension.class)
class CountriesServiceTest {

    @Mock
    private CountriesRepository countriesRepository;


    @InjectMocks
    private CountriesService countriesService;



    @Test
    public void shouldAddNewCountryToBase() {



    }

    @Test
    void refactorCountryName() {
    }
}