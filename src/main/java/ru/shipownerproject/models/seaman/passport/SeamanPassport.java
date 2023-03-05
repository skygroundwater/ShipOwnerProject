package ru.shipownerproject.models.seaman.passport;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.shipownerproject.models.seaman.Seaman;

import java.io.Serializable;

@Entity
@Table(name="passports")
@Getter
@Setter
public class SeamanPassport implements Serializable {

    @Column(name="serialandnumber")
    @NotEmpty(message = "Serial and number of seaman passport is important to insert")
    private String passport;

    @OneToOne
    @Id
    @JoinColumn(name = "seaman_id", referencedColumnName = "id")
    private Seaman seaman;

    public SeamanPassport(){}


    public SeamanPassport(Seaman seaman, String serialAndNumber){
        this.passport = serialAndNumber;
        this.seaman = seaman;
    }

    public SeamanPassport(String serialAndNumber){
        this.passport = serialAndNumber;
    }

    public String toString(){
        return "Passport: " + passport
                + "\n Seaman: " + seaman.getInfo();
    }

}
