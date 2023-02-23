package ru.shipownerproject.models.vessels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;

import java.util.List;

@Entity
@Table(name = "vessel")
@Getter
@Setter
public class Vessel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "shipowner", referencedColumnName = "id")
    private ShipOwner shipOwner;

    @OneToMany(mappedBy = "vessel", cascade = CascadeType.ALL)
    private List<Seaman> seamen;

    @Column(name="name")
    private String name;

    @Column(name = "IMO")
    private String IMO;

    @Override
    public String toString(){
        return name + " " + IMO + " " + shipOwner.getName() + " Country of registered: " + country.getName();
    }

    public Vessel(){}

    public Vessel(String name, String IMO){
        this.name = name;
        this.IMO = IMO;
    }

}
