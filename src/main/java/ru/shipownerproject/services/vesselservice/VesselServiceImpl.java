package ru.shipownerproject.services.vesselservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.databases.seamandatabase.SeamanRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnerRepository;
import ru.shipownerproject.databases.vesselrepository.VesselRepository;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

@Service
public class VesselServiceImpl implements VesselService {

    private final VesselRepository vesselRepository;

    private final ShipOwnerRepository shipOwnerRepository;
    private final CountryRepository countryRepository;
    private final SeamanRepository seamanRepository;

    public VesselServiceImpl(VesselRepository vesselRepository, ShipOwnerRepository shipOwnerRepository,
                             CountryRepository countryRepository,
                             SeamanRepository seamanRepository) {
        this.vesselRepository = vesselRepository;
        this.shipOwnerRepository = shipOwnerRepository;
        this.countryRepository = countryRepository;
        this.seamanRepository = seamanRepository;
    }

    @Override
    public String vessel(String name) {
        Vessel vessel = vesselRepository.findByName(name)
                .stream().findAny().orElse(null);
        if (vessel == null) return "This vessel is not available";
        Country country = countryRepository.findById(
                vessel.getCountry().getId()).orElse(null);
        if (country == null) return "Ship Owner cannot to be without country";
        ShipOwner shipOwner = shipOwnerRepository
                .findByName(vessel.getShipOwner().getName())
                .stream().findAny().orElse(null);
        if (shipOwner == null) return "Vessel cannot to be without shipowner";
        else return "Vessel: <<" + vessel.getName() + ">> \n" +
                " " + vessel.getIMO() + "\n" +
                "Country of registration: "
                + country.getName() + " Ship Owner: " + vessel.getShipOwner().getName();
    }

    @Override
    public String addNewVessel(String vesselName, String shipOwnerName,
                               String IMO) {
        ShipOwner shipOwner = shipOwnerRepository.findByName(shipOwnerName)
                .stream().findAny().orElse(null);
        Vessel vessel = vesselRepository.findByIMO(IMO)
                .stream().findAny().orElse(null);
        if (shipOwner != null) {
            if (vessel == null) {
                Vessel newVessel = new Vessel(vesselName, IMO);
                Country country = countryRepository.findByName
                                (shipOwner.getCountry().getName()).stream()
                        .findAny().orElse(null);
                newVessel.setShipOwner(shipOwner);
                newVessel.setCountry(country);
                vesselRepository.save(newVessel);
                return "New vessel is registered for Ship Owner: " + shipOwner.getName();
            } else {
                return "This vessel is already in base. Choose from list.";
            }
        } else {
            return "We can not register this vessel. \n" +
                    " Because required Ship Owner is not available";
        }
    }

    @Override
    public String removeVesselFromBase(String IMO) {
        Vessel vessel = vesselRepository.findByIMO(IMO)
                .stream().findAny().orElse(null);
        if (vessel != null) {
            vesselRepository.delete(vessel);
            return "Vessel have been deleted from base";
        }
        return "Vessel is not available";
    }
}