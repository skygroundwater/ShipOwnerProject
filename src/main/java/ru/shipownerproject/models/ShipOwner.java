package ru.shipownerproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shipowners")
@Getter
@Setter
public class ShipOwner implements Serializable {

    @Id
    @Column(name = "name")
    @NotEmpty(message = "The name of shipowner is required to fill")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "The description of shipowner is required to fill")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country", referencedColumnName = "name")
    private Country country;

    @OneToMany(mappedBy = "shipOwner", cascade = CascadeType.ALL)
    private List<Vessel> vessels;

    @OneToMany(mappedBy = "shipowner", cascade = CascadeType.ALL)
    private List<Seaman> seamen;

    @Override
    public String toString() {
        return "Ship Owner: " + name + "\n"
                + description;
    }

    public ShipOwner() {}

    public ShipOwner(String name){
        this.name = name;
    }

    public ShipOwner(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public ShipOwner(String name, String description, String countryName) {
        this.name = name;
        this.description = description;
        this.setCountry(new Country(countryName));
    }
    public ShipOwner(String name, String description, Country country) {
        this.name = name;
        this.description = description;
        this.setCountry(country);
    }
}