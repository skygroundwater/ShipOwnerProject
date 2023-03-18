package ru.shipownerproject.utils.$dto.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shipownerproject.utils.$dto.VesselDTO;

import java.util.Objects;

@Component
public class VesselDTOValidator implements Validator {

    private boolean checkIMOStandard(Integer IMO) {
        String[] strings = String.valueOf(IMO).split("");
        int count = 0;
        int i = 0;
        for (int u = strings.length; u > strings.length - 6; u--) {
            count = count + (Integer.parseInt(strings[i++]) * u);
        }
        String[] strings2 = String.valueOf(count).split("");
        return !Objects.equals(strings2[strings2.length - 1], strings[strings.length - 1]);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return VesselDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VesselDTO vessel = (VesselDTO) target;
        if (vessel.getIMO() == null || String.valueOf(vessel.getIMO()).length() != 7 || checkIMOStandard(vessel.getIMO())) {
            errors.rejectValue("IMO", "",
                    "IMO Number should have 7 numbers, should meet the standards and to be unique for every vessel");
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