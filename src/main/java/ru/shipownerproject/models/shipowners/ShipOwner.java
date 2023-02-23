package ru.shipownerproject.models.shipowners;

import jakarta.persistence.*;
import lombok.*;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

@Entity
@Table(name = "shipowner")
@Getter
@Setter
public class ShipOwner {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "shipOwner", cascade = CascadeType.ALL)
    private List<Vessel> vessels;

    @OneToMany(mappedBy = "shipowner", cascade = CascadeType.ALL)
    private List<Seaman> seamen;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public String toString(){
        return "Ship Owner: " + name + "\n"
                + description;
    }

    public ShipOwner(){}

    public ShipOwner(String name, String description){
        this.name = name;
        this.description=description;
    }


}
