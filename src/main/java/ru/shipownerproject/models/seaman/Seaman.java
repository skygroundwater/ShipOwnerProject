package ru.shipownerproject.models.seaman;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.shipownerproject.models.countries.Country;
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

    public Seaman(){}

    @Override
    public String toString(){
        return fullName + " " + position;
    }

    public String getInfo(){
        return fullName + " Position: " + position
                + "\n Date of birth: " + birth
                + "\n Place of birth: " + birthPlace
                + "\n Citizenship: " + citizenship.getName()
                + "\n Company of work: " + vessel.getShipOwner().getName()
                + "\n Vessel of work: " + vessel.getName();
    }


}
