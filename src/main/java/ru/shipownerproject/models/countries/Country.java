package ru.shipownerproject.models.countries;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<ShipOwner> shipOwners;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Vessel> vessels;

    @OneToMany(mappedBy = "citizenship", cascade = CascadeType.ALL)
    private List<Seaman> seamen;

    @Column(name = "name")
    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
