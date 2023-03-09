package ru.shipownerproject.models.countries.ports;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shipownerproject.models.countries.Country;
import ru.shipownerproject.models.vessels.Vessel;

import java.util.List;

@Entity
@Table(name = "ports")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Port {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Port cannot to be without name")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Port cannot to be without navigational description")
    @Column(name = "nav_description")
    private String nav_description;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "port", cascade = CascadeType.ALL)
    private List<Vessel> regVessels;



    public Port(String name, Country country, String nav_description){
        this.name = name;
        this.country = country;
        this.nav_description = nav_description;
    }

}
