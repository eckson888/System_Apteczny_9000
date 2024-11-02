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
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private String commonName;
    private String pharmaceuticalForm;
    //@NotBlank(message = "Nazwa leku jest wymagana") w niektorych lekach nie ma substancji aktywnej
    //to na dole ważne bo wypierdala błąd jak tego nie ma
    @Column(updatable = true, name="activeSubstance", nullable = false, columnDefinition = "varchar(10000)")
    private String activeSubstance;

    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<DrugstoreItem> inventory = new ArrayList<>();

    public Drug(int id){this.id = id;}




}
