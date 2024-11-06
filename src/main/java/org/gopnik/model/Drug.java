package org.gopnik.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Data
@Table(name = "drug")   //tego nie bylo wczesniej ale juz smiga lol
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private String commonName;
    private String pharmaceuticalForm;
    //to na dole ważne bo wypierdala błąd jak tego nie ma
    @Column(updatable = true, name="activeSubstance", nullable = false, columnDefinition = "varchar(10000)") //XDDD varchar(1000000000000000000)
    private String activeSubstance;



    public Drug(int id){this.id = id;}




}
