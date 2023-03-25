package ru.shipownerproject.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shipownerproject.models.Country;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.models.ShipOwner;
import ru.shipownerproject.models.Vessel;
import ru.shipownerproject.models.enums.VesselType;
import ru.shipownerproject.repositories.CountriesRepository;
import ru.shipownerproject.services.countryservice.CountriesServiceImpl;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.ListIsEmptyException;
import ru.shipownerproject.utils.exceptions.NotCreatedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.THAT_COUNTRY;

@ExtendWith(MockitoExtension.class)
class CountriesServiceImplTest {

    @Mock
    private CountriesRepository countriesRepository;

    @InjectMocks
    private CountriesServiceImpl out;

    private static final Country RUSSIA = new Country("Russia");

    private static final Country USA = new Country("United States of America");

    private static final Country DENMARK = new Country("Denmark");

    private static final Country FRANCE = new Country("France");

    private static final Country CYPRUS = new Country("Cyprus");

    private static final Country WRONG_COUNTRY = new Country();

    private static final ShipOwner PORT_FLEET = new ShipOwner("PORT FLEET", "Saint-Petersburg Tug Company", RUSSIA);

    private static final ShipOwner GRIPHON = new ShipOwner("GRIPHON", "Saint-Petersburg Tug Company", RUSSIA);

    private static final ShipOwner BMBA = new ShipOwner("BMBA", "Luga's Tug Company", RUSSIA);

    private static final ShipOwner BALTIC_TUGS = new ShipOwner("BALTIC TUGS", "Saint-Petersburg Tug Company", RUSSIA);
    private static final ShipOwner CMA_CGM = new ShipOwner("CMA CGM",
            "French transport company, mainly engaged in container shipping", FRANCE);
    private static final Port portSPB = new Port("Big Port of Saint-Petersburg", RUSSIA, "Saint-Petersburg Port");

    private static final Port portLimassol = new Port("Limassol", CYPRUS, "Is situated on the S coast of Cyprus at the head of the Akrotiri bay");

    private static final Port portUst_Luga = new Port("Port of Ust'-Luga", RUSSIA, "Navigation in the seaport is carried out in the following hydrometeorological conditions");

    private static final Vessel BELUGA = new Vessel("BELUGA", 9402160, BMBA, VesselType.TUG, RUSSIA, portUst_Luga,  LocalDate.of(2007, 10,8));

    private static final Vessel GRIPHON_5 = new Vessel("GRIPHON-5", 9402146, GRIPHON, VesselType.TUG, RUSSIA, portSPB,  LocalDate.of(2007, 10,8));

    private static final Vessel GRIPHON_7 = new Vessel("GRIPHON-7", 9548847, GRIPHON, VesselType.TUG, RUSSIA, portSPB,  LocalDate.of(2009, 10,8));

    private static final Vessel TORNADO = new Vessel("TORNADO", 9394193, BALTIC_TUGS, VesselType.TUG, RUSSIA, portSPB,  LocalDate.of(2015, 10,8));

    private static final Vessel CMA_CGM_LOUGA = new Vessel("CMA CGM LOUGA", 9745550, CMA_CGM, VesselType.CONTAINER, FRANCE, portLimassol,  LocalDate.of(2018, 10,8));
    private static final List<Country> countries = new ArrayList<>(List.of(RUSSIA, DENMARK, USA));

    private static final List<ShipOwner> russianShipOwners = new ArrayList<>(List.of(PORT_FLEET, GRIPHON, BALTIC_TUGS));

    private static final List<ShipOwner> frenchShipOwners = new ArrayList<>(List.of(CMA_CGM));

    private static final List<Vessel> russianVessels = new ArrayList<>(List.of(GRIPHON_5, GRIPHON_7, BELUGA));

    private static final List<Vessel> frenchVessels = new ArrayList<>(List.of(CMA_CGM_LOUGA));

    @Test
    public void should_ThrowListIsEmptyException_IfReturnNull() throws ListIsEmptyException {
        when(countriesRepository.findAll()).thenReturn(null);
        Throwable throwable = assertThrows(ListIsEmptyException.class, () -> {
            out.allCountries();
        });
        assertNotNull(throwable.getMessage());
        assertEquals("List of countries for that project is empty", throwable.getMessage());
    }

    @Test
    public void should_ThrowListIsEmptyException_IfReturnEmptyList() throws ListIsEmptyException {
        when(countriesRepository.findAll()).thenReturn(Collections.emptyList());
        Throwable throwable = assertThrows(
                ListIsEmptyException.class,
                () -> {
                    out.allCountries();
                });
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
    public void should_ThrowListIsEmptyException_WhenUserWantToGetShipOwnersOfCountry(){
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
    public void should_ReturnListOfShipownersOfCountry(){
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
    public void should_ThrowListIsEmptyException_WhenUserWantToGetVesselsOfCountry(){
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
    public void should_ReturnListOfVesselsOfCountry(){
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

}