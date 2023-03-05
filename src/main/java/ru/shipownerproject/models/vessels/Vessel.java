package ru.shipownerproject.models.vessels;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "vessel")
@Getter
@Setter
public class Vessel implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "shipowner", referencedColumnName = "id")
    private ShipOwner shipOwner;

    @OneToMany(mappedBy = "vessel", cascade = CascadeType.ALL)
    private List<Seaman> seamen;

    @Column(name = "name")
    @NotEmpty(message = "Vessel cannot to be without name")
    private String name;

    @Column(name = "IMO")
    @NotEmpty(message = "IMO Number is required before building the vessel")
    private String IMO;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private VesselType vesselType;

    @Override
    public String toString() {
        return "\nVessel: <<" + getName() + ">> \n" +
                "IMO Number " + getIMO() + "\n" +
                "Country of registration: " + getCountry().getName() + "\n" +
                "Ship Owner: " + getShipOwner().getName() + "\n";
    }

    public Vessel() {
    }

    public Vessel(String IMO, String name){
        this.IMO = IMO;
        this.name = name;
    }

    public Vessel(String name, String IMO, ShipOwner shipOwner, VesselType vesselType, Country country) {
        this.name = name;
        this.IMO = IMO;
        this.vesselType = vesselType;
        this.shipOwner = shipOwner;
        this.country = country;
    }
}