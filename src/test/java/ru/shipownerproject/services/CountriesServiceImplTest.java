package ru.shipownerproject.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shipownerproject.models.Country;
import ru.shipownerproject.models.Seaman;
import ru.shipownerproject.models.ShipOwner;
import ru.shipownerproject.models.Vessel;
import ru.shipownerproject.repositories.CountriesRepository;
import ru.shipownerproject.services.countryservice.CountriesServiceImpl;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.ListIsEmptyException;
import ru.shipownerproject.utils.exceptions.NotCreatedException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ru.shipownerproject.constants.Constants.*;
import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.THAT_COUNTRY;

@ExtendWith(MockitoExtension.class)
class CountriesServiceImplTest {

    @Mock
    private CountriesRepository countriesRepository;

    @InjectMocks
    private CountriesServiceImpl out;


    @Test
    public void should_ThrowListIsEmptyException_IfReturnNull() throws ListIsEmptyException {
        when(countriesRepository.findAll()).thenReturn(null);
        Throwable throwable = assertThrows(ListIsEmptyException.class, () -> out.allCountries());
        assertNotNull(throwable.getMessage());
        assertEquals("List of countries for that project is empty", throwable.getMessage());
    }

    @Test
    public void should_ThrowListIsEmptyException_IfReturnEmptyList() throws ListIsEmptyException {
        when(countriesRepository.findAll()).thenReturn(Collections.emptyList());
        Throwable throwable = assertThrows(
                ListIsEmptyException.class,
                () -> out.allCountries());
        assertNotNull(throwable.getMessage());
        assertEquals("List of countries for that project is empty", throwable.getMessage());
    }

    @Test
    public void should_ReturnAllCountries_FromDB() {
        when(countriesRepository.findAll()).thenReturn(countries);
        List<Country> comparingList = out.allCountries();
        assertNotNull(comparingList);
        assertEquals(3, comparingList.size());
        assertTrue(comparingList.containsAll(countries));
        assertEquals(countries, comparingList);
    }

    @Test
    public void should_AddNewCountry_ToDB() {
        when(countriesRepository.save(DENMARK)).thenReturn(DENMARK);
        Country country = out.newCountry(DENMARK);
        assertNotNull(country);
        assertEquals("Denmark", country.getName());
        assertEquals(country, DENMARK);
    }

    @Test
    public void should_ThrowAlreadyAddedToBaseException_IfExists() {
        when(countriesRepository.save(USA)).thenThrow(new AlreadyAddedToBaseException(THAT_COUNTRY));
        Throwable throwable = assertThrows(AlreadyAddedToBaseException.class,
                () -> out.newCountry(USA));
        assertNotNull(throwable.getMessage());
        assertEquals("That country is already added to base.", throwable.getMessage());
    }

    @Test
    public void should_ThrowAnyException_WhenUserGoingToAddWrongCountry() {
        Throwable throwable = assertThrows(NotCreatedException.class,
                () -> out.newCountry(WRONG_COUNTRY));
        assertEquals(throwable.getClass(), NotCreatedException.class);
        assertNotNull(throwable);
        assertNotNull(throwable.getMessage());
        assertEquals("Country cannot to be without name hasn't been created after request",
                throwable.getMessage());
    }

    @Test
    public void should_ThrowListIsEmptyException_WhenUserWantToGetShipOwnersOfCountry() {
        when(countriesRepository.findByNameWithShipOwners(DENMARK.getName()))
                .thenThrow(new ListIsEmptyException(THAT_COUNTRY));
        Throwable throwable = assertThrows(ListIsEmptyException.class,
                () -> out.returnShipOwnersRegisteredInCountry(DENMARK.getName()));
        assertEquals(throwable.getClass(), ListIsEmptyException.class);
        assertNotNull(throwable);
        assertNotNull(throwable.getMessage());
        assertEquals("List of That country is empty", throwable.getMessage());
    }

    @Test
    public void should_ReturnListOfShipownersOfCountry() {
        RUSSIA.setShipOwners(russianShipOwners);
        FRANCE.setShipOwners(frenchShipOwners);
        when(countriesRepository.findByNameWithShipOwners(RUSSIA.getName())).thenReturn((List.of(RUSSIA)));
        when(countriesRepository.findByNameWithShipOwners(FRANCE.getName())).thenReturn((List.of(FRANCE)));
        List<ShipOwner> testingRussianShipOwners = out.returnShipOwnersRegisteredInCountry(RUSSIA.getName());
        List<ShipOwner> testingFrenchShipowners = out.returnShipOwnersRegisteredInCountry(FRANCE.getName());
        assertNotNull(testingRussianShipOwners);
        assertNotNull(testingFrenchShipowners);
        assertEquals(russianShipOwners, testingRussianShipOwners);
        assertEquals(frenchShipOwners, testingFrenchShipowners);
    }

    @Test
    public void should_ThrowListIsEmptyException_WhenUserWantsToGetVesselsOfCountry() {
        when(countriesRepository.findByNameWithVessels(DENMARK.getName()))
                .thenThrow(new ListIsEmptyException(THAT_COUNTRY));
        Throwable throwable = assertThrows(ListIsEmptyException.class,
                () -> out.returnVesselsRegisteredInCountry(DENMARK.getName()));
        assertEquals(throwable.getClass(), ListIsEmptyException.class);
        assertNotNull(throwable);
        assertNotNull(throwable.getMessage());
        assertEquals("List of That country is empty", throwable.getMessage());
    }

    @Test
    public void should_ReturnListOfVesselsOfCountry() {
        RUSSIA.setVessels(russianVessels);
        FRANCE.setVessels(frenchVessels);
        when(countriesRepository.findByNameWithVessels(RUSSIA.getName()))
                .thenReturn(List.of(RUSSIA));
        when(countriesRepository.findByNameWithVessels(FRANCE.getName()))
                .thenReturn(List.of(FRANCE));
        List<Vessel> testingRussianVessels =
                out.returnVesselsRegisteredInCountry(RUSSIA.getName());
        List<Vessel> testingFrenchVessels =
                out.returnVesselsRegisteredInCountry(FRANCE.getName());
        assertNotNull(testingRussianVessels);
        assertNotNull(testingFrenchVessels);
        assertEquals(russianVessels, testingRussianVessels);
        assertEquals(frenchVessels, testingFrenchVessels);
    }

    @Test
    public void should_ThrowListIsEmptyException_WhenUserWantsToGetSeamenWithCitizenshipOfCountry() {
        when(countriesRepository.findByNameWithSeamen(DENMARK.getName()))
                .thenThrow(new ListIsEmptyException(THAT_COUNTRY));
        Throwable throwable = assertThrows(ListIsEmptyException.class,
                () -> out.seamenWithCitizenShipOfCountry(DENMARK.getName()));
        assertEquals(throwable.getClass(), ListIsEmptyException.class);
        assertNotNull(throwable);
        assertNotNull(throwable.getMessage());
        assertEquals("List of That country is empty", throwable.getMessage());
    }

    @Test
    public void should_ReturnListOfSeamenOfCountry() {
        RUSSIA.setSeamen(seamen);
        when(countriesRepository.findByNameWithSeamen(RUSSIA.getName()))
                .thenReturn(List.of(RUSSIA));
        List<Seaman> testingSeamenList =
                out.seamenWithCitizenShipOfCountry(RUSSIA.getName());
        assertNotNull(testingSeamenList);
        assertEquals(testingSeamenList, seamen);
    }
}