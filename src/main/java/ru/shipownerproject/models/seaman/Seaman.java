package ru.shipownerproject.models.seaman;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.passport.SeamanPassport;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "seamen")
@AllArgsConstructor
@Setter
@Getter
public class Seaman implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipowner", referencedColumnName = "name")
    private ShipOwner shipowner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenship", referencedColumnName = "name")
    private Country citizenship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel", referencedColumnName = "IMO")
    private Vessel vessel;

    @Column(name = "fullname")
    @NotEmpty(message = "Name for seaman should be entered")
    private String fullName;

    @Column(name = "position")
    @NotEmpty(message = "Seaman cannot work on a vessel without position")
    private String position;

    @Column(name = "birthdate")
    @NotNull(message = "Enter birth date")
    private Date birth;

    @Column(name = "birthplace")
    @NotEmpty(message = "Enter birth place")
    private String birthPlace;

    @OneToOne(mappedBy = "seaman")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private SeamanPassport seamanPassport;

    public Seaman() {}

    public Seaman(String fullName, String position, Vessel vessel, Country citizenship,
                  Date birth, String birthPlace, ShipOwner shipOwner, String passport){
        this.fullName = fullName;
        this.position = position;
        this.vessel = vessel;
        this.citizenship = citizenship;
        this.birth = birth;
        this.birthPlace = birthPlace;
        this.shipowner = shipOwner;
        setSeamanPassport(new SeamanPassport(this, passport));
    }


    public void setSeamanPassport(SeamanPassport seamanPassport){
        this.seamanPassport = seamanPassport;
        seamanPassport.setSeaman(this);
    }

    @Override
    public String toString() {
        return fullName + " " + position + " Passport number: " + seamanPassport.getPassport();
    }

    public String getInfo() {
        return fullName + " Position: " + position
                + "\n Date of birth: " + birth
                + "\n Place of birth: " + birthPlace
                + "\n Citizenship: " + citizenship.getName()
                + "\n Company of work: " + vessel.getShipOwner().getName()
                + "\n Vessel of work: " + vessel.getName();
    }
}