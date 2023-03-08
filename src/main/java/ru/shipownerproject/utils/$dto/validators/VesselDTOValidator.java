package ru.shipownerproject.utils.$dto.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.databases.countrybase.CountriesRepository;
import ru.shipownerproject.databases.shipownerdatabase.ShipOwnersRepository;
import ru.shipownerproject.utils.$dto.VesselDTO;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import static ru.shipownerproject.services.countryservice.CountriesServiceImpl.NC;
import static ru.shipownerproject.services.shipsownerservice.ShipOwnersServiceImpl.NS;

@Component
public class VesselDTOValidator implements Validator {

    private final ShipOwnersRepository shipOwnersRepository;

    private final CountriesRepository countriesRepository;

    public VesselDTOValidator(ShipOwnersRepository shipOwnersRepository,
                              CountriesRepository countriesRepository) {
        this.shipOwnersRepository = shipOwnersRepository;
        this.countriesRepository = countriesRepository;
    }

    private void checkShipOwnerByName(String shipOwnerName) {
        shipOwnersRepository.findByName(shipOwnerName).stream().findAny().orElseThrow(()
                -> new NotFoundInBaseException(NS));
    }

    private void checkCountryByName(String countryName){
        countriesRepository.findByName(countryName).stream().findAny().orElseThrow(()
                -> new NotFoundInBaseException(NC));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return VesselDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VesselDTO vessel = (VesselDTO) target;
        checkShipOwnerByName(vessel.getShipOwner().getName());
        checkCountryByName(vessel.getCountry().getName());
        if (vessel.getIMO() == null || String.valueOf(vessel.getIMO()).length() != 7) {
            errors.rejectValue("IMO", "",
                    "IMO Number should have 7 numbers and to be unique for every vessel");
        }
        if (vessel.getShipOwner() == null || vessel.getShipOwner().getName().isEmpty()) {
            errors.rejectValue("shipOwner", "",
                    "Vessel cannot to be without shipowner");
        }
        if (vessel.getCountry() == null || vessel.getCountry().getName().isEmpty()) {
            errors.rejectValue("country", "",
                    "Vessel cannot to be without country of registration");
        }
        if (vessel.getName() == null || vessel.getName().isEmpty()) {
            errors.rejectValue("name", "",
                    "Vessel cannot to be without name");
        }
        if (vessel.getType() == null || vessel.getType().isEmpty()) {
            errors.rejectValue("type", "", "Vessel cannot to be non type");
        }
        if (vessel.getBuildingDate() == null) {
            errors.rejectValue("buildingDate", "", "Enter building date");
        }
    }
}