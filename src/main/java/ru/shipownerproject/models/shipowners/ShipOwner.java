package ru.shipownerproject.models.shipowners;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shipowner")
@Getter
@Setter
public class ShipOwner implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "The name of shipowner is required to fill")
    private String name;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "shipOwner", cascade = CascadeType.ALL)
    private List<Vessel> vessels;

    @OneToMany(mappedBy = "shipowner", cascade = CascadeType.ALL)
    private List<Seaman> seamen;

    @Column(name = "description")
    @NotEmpty(message = "The description of shipowner is required to fill")
    private String description;

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