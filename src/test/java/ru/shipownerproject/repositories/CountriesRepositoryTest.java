package ru.shipownerproject.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.shipownerproject.models.Country;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CountriesRepositoryTest {

    @Autowired
    private CountriesRepository out;

    private static final Country RUSSIA = new Country("Russia");

    @Test
    void shouldSelectCountryByNameFromDB_IfExists() {
        out.save(RUSSIA);

        Country country = out.findByName(RUSSIA.getName()).stream().findAny().orElseThrow();

        assertThat(country).isNotNull();
    }
}