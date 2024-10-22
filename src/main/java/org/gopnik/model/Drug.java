package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Nazwa leku jest wymagana")
    private String name;
    @NotBlank(message = "Nazwa leku jest wymagana")
    private String commonName;
    @NotBlank(message = "Nazwa leku jest wymagana")
    private String pharmaceuticalForm;
    @NotBlank(message = "Nazwa leku jest wymagana")
    private String activeSubstance;

    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<DrugstoreItem> inventory = new ArrayList<>();

    public Drug(int id){this.id = id;}


}
