package ru.shipownerproject.services.countryservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.shipownerproject.databases.countrybase.CountryRepository;

class CountriesServiceImplTest {

    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private CountriesServiceImpl countriesService = new CountriesServiceImpl(countryRepository);




    @Test
    void shouldAddNewCountryToBase() {

    }

    @Test
    void refactorCountryName() {
    }
}