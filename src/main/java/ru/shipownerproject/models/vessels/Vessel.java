package ru.shipownerproject.models.vessels;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.Seaman;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.type.VesselType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
    @NotNull
    @Length(min = 7, max = 7, message = "IMO Number should have 7 numbers and to be unique for every vessel")
    private Integer IMO;

    @Enumerated(EnumType.STRING)
    private VesselType vesselType;

    @Column(name = "building_date")
    @NotNull(message = "Enter date of building")
    private LocalDate dateOfBuild;

    @Override
    public String toString() {
        return "\nVessel: <<" + getName() + ">> \n" +
                "IMO Number " + getIMO() + "\n" +
                "Country of registration: " + getCountry().getName() + "\n" +
                "Ship Owner: " + getShipOwner().getName() + "\n";
    }

    public Vessel() {
    }

    public Vessel(Integer IMO, String name){
        this.IMO = IMO;
        this.name = name;
    }

    public Vessel(String name, Integer IMO, ShipOwner shipOwner, VesselType vesselType, Country country, LocalDate buildingDate) {
        this.name = name;
        this.IMO = IMO;
        this.vesselType = vesselType;
        this.shipOwner = shipOwner;
        this.country = country;
        this.dateOfBuild = buildingDate;
    }
}