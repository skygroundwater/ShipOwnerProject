package ru.shipownerproject.models.seaman;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.seaman.passport.SeamanPassport;
import ru.shipownerproject.models.shipowners.ShipOwner;
import ru.shipownerproject.models.vessels.Vessel;

@Entity
@Table(name = "seamen")
@AllArgsConstructor
@Setter
@Getter
public class Seaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipowner", referencedColumnName = "id")
    private ShipOwner shipowner;

    @ManyToOne
    @JoinColumn(name = "citizenship", referencedColumnName = "id")
    private Country citizenship;

    @ManyToOne
    @JoinColumn(name = "vessel", referencedColumnName = "id")
    private Vessel vessel;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "position")
    private String position;

    @Column(name = "birthdate")
    private String birth;

    @Column(name = "birthplace")
    private String birthPlace;

    @OneToOne(mappedBy = "seaman")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private SeamanPassport seamanPassport;

    public Seaman() {
    }

    public Seaman(String fullName, String position, Vessel vessel, Country citizenship,
                  String birth, String birthPlace, ShipOwner shipOwner, String passport){
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