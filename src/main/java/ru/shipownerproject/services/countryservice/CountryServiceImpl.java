package ru.shipownerproject.services.countryservice;

import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.countrybase.CountryRepository;
import ru.shipownerproject.exceptions.AlreadyHaveCountryException;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;


    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll(){
        return countryRepository.findAll();
    }

    @Override
    public String newCountry(String name){
        try {
            if (findByName(name) != null) throw new AlreadyHaveCountryException();
            else countryRepository.save(new Country(name));
        }catch (AlreadyHaveCountryException e){
            return ""+e.getMessage();
        }
        return "Country is added to database";
    }

    @Override
    public String oneCountry(String name){
        Country country = countryRepository.findByName(name)
                .stream().findAny().orElse(null);
        if(country == null) return "This country is not available";
       else return "Country: " + country.getName();
    }

    @Override
    public Country findByName(String name){
        return countryRepository.findAll().stream().filter(country ->
                country.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public String countryShipOwners(String name){
        StringBuilder stringBuilder = new StringBuilder();
        for(ShipOwner shipOwner: countryRepository.findByName(name)
                .stream().findAny().get().getShipOwners()){
            stringBuilder.append("\n")
                    .append(shipOwner.getName())
                    .append("\n");
        }
        return String.valueOf(stringBuilder);
    }

    @Override
    public String countryVessels(Integer id){
        Country country = countryRepository.findById(id).orElse(null);
        if(country == null) return "This country is not Available";
        StringBuilder stringBuilder = new StringBuilder();
        for(Vessel vessel: country.getVessels()){
            stringBuilder.append(vessel).append("\n ");
        }
        return String.valueOf(stringBuilder);
    }

    @Override
    public String refactorCountryName(String oldCountryName, String newCountryName){
        Country country = countryRepository.findByName(oldCountryName)
                .stream().findAny().orElse(null);
        if(country == null){
            return "This country is not Available";
        }else {
            country.setName(newCountryName);
            countryRepository.save(country);
            return "Name of this country has been refactored";
        }
    }

}
