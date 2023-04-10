package ru.shipownerproject.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shipownerproject.models.Port;
import ru.shipownerproject.repositories.CountriesRepository;
import ru.shipownerproject.repositories.PortsRepository;
import ru.shipownerproject.repositories.VesselsRepository;
import ru.shipownerproject.services.countryservice.CountriesService;
import ru.shipownerproject.services.portservice.PortsServiceImpl;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ru.shipownerproject.constants.Constants.*;
import static ru.shipownerproject.services.portservice.PortsServiceImpl.NP;

@ExtendWith(MockitoExtension.class)
class PortsServiceImplTest {
    @Mock
    private PortsRepository portsRepository;

    @Mock
    private VesselsRepository vesselsRepository;

    @Mock
    private CountriesService countriesService;

    @InjectMocks
    private PortsServiceImpl out;

    @Test
    public void should_ReturnPort_WhenUserSearchByName() {
        when(portsRepository.findByName(portLimassol.getName()))
                .thenReturn(List.of(portLimassol));
        Port testingPort = out.findPortByName(portLimassol.getName());
        assertNotNull(testingPort);
        assertEquals(testingPort, portLimassol);
    }

    @Test
    public void should_ThrowNotFoundInDataBaseException() {
        when(portsRepository.findByName("Morocco"))
                .thenThrow(new NotFoundInBaseException(NP));
        Throwable throwable = assertThrows(NotFoundInBaseException.class,
                () -> out.findPortByName("Morocco"));
        assertNotNull(throwable);
        assertEquals(throwable.getMessage(),
                "This port is not available. At first it should be in the data base.");
    }

    @Test
    void refactorPort() {
    }

    @Test
    void vesselRegisteredThisPort() {
    }

    @Test
    void deletePortFromDB() {
    }
}