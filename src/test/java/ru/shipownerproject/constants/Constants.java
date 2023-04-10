package ru.shipownerproject.constants;

import org.springframework.security.core.parameters.P;
import ru.shipownerproject.models.*;
import ru.shipownerproject.models.enums.VesselType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constants {

    public static final Country RUSSIA = new Country("Russia");

    public static final Country USA = new Country("United States of America");

    public static final Country DENMARK = new Country("Denmark");

    public static final Country FRANCE = new Country("France");

    public static final Country CYPRUS = new Country("Cyprus");

    public static final Country WRONG_COUNTRY = new Country();

    public static final ShipOwner PORT_FLEET = new ShipOwner("PORT FLEET", "Saint-Petersburg Tug Company", RUSSIA);

    public static final ShipOwner GRIPHON = new ShipOwner("GRIPHON", "Saint-Petersburg Tug Company", RUSSIA);

    public static final ShipOwner BMBA = new ShipOwner("BMBA", "Luga's Tug Company", RUSSIA);

    public static final ShipOwner BALTIC_TUGS = new ShipOwner("BALTIC TUGS", "Saint-Petersburg Tug Company", RUSSIA);

    public static final ShipOwner CMA_CGM = new ShipOwner("CMA CGM",
            "French transport company, mainly engaged in container shipping", FRANCE);
    public static final Port portSPB = new Port("Big Port of Saint-Petersburg", RUSSIA, "Saint-Petersburg Port");

    public static final Port portLimassol = new Port("Limassol", CYPRUS, "Is situated on the S coast of Cyprus at the head of the Akrotiri bay");

    public static final Port portUst_Luga = new Port("Port of Ust'-Luga", RUSSIA, "Navigation in the seaport is carried out in the following hydrometeorological conditions");

    public static final Vessel BELUGA = new Vessel("BELUGA", 9402160, BMBA, VesselType.TUG, RUSSIA, portUst_Luga, LocalDate.of(2007, 10, 8));

    public static final Vessel GRIPHON_5 = new Vessel("GRIPHON-5", 9402146, GRIPHON, VesselType.TUG, RUSSIA, portSPB, LocalDate.of(2007, 10, 8));

    public static final Vessel GRIPHON_7 = new Vessel("GRIPHON-7", 9548847, GRIPHON, VesselType.TUG, RUSSIA, portSPB, LocalDate.of(2009, 10, 8));

    public static final Vessel TORNADO = new Vessel("TORNADO", 9394193, BALTIC_TUGS, VesselType.TUG, RUSSIA, portSPB, LocalDate.of(2015, 10, 8));

    public static final Vessel CMA_CGM_LOUGA = new Vessel("CMA CGM LOUGA", 9745550, CMA_CGM, VesselType.CONTAINER, FRANCE, portLimassol, LocalDate.of(2018, 10, 8));

    public static final List<Country> countries = new ArrayList<>(List.of(RUSSIA, DENMARK, USA));

    public static final List<ShipOwner> russianShipOwners = new ArrayList<>(List.of(PORT_FLEET, GRIPHON, BALTIC_TUGS));

    public static final List<ShipOwner> frenchShipOwners = new ArrayList<>(List.of(CMA_CGM));

    public static final List<Vessel> russianVessels = new ArrayList<>(List.of(GRIPHON_5, GRIPHON_7, BELUGA));

    public static final List<Vessel> frenchVessels = new ArrayList<>(List.of(CMA_CGM_LOUGA));

    public static final Seaman ANDREY = new Seaman("Petrov Andrey Gennadievich", "THIRD OFFICER", GRIPHON_5, RUSSIA, new Date(1996- 6 - 6), "Novoe Kuzemkino", GRIPHON, 1997808);

    public static final Seaman OLEG = new Seaman("Metelev Oleg Sergeevich", "CHIEF OFFICER", GRIPHON_5, RUSSIA, new Date(1996- 6 - 6), "Novoe Kuzemkino", GRIPHON, 1997808);

    public static final Seaman SERGEY = new Seaman("Kashirskiy Sergey Aleksandrovich", "CHIEF ENGINEER", GRIPHON_5, RUSSIA, new Date(1996- 6 - 6), "Novoe Kuzemkino", GRIPHON, 1997808);

    public static final List<Seaman> seamen = new ArrayList<>(List.of(ANDREY, OLEG, SERGEY));

    public static final List<Port> russianPorts = new ArrayList<>(List.of(portSPB, portUst_Luga));

    public static final List<Port> cyprusPorts = new ArrayList<>(List.of(portLimassol));

    private Constants(){}


}
