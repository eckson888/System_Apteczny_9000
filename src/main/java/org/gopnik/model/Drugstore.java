package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="drugstores")
public class Drugstore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ID apteki jest wymagane")
    private String drugstoreID;

    @OneToMany(mappedBy = "drugstore", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<DrugstoreItem> inventory = new ArrayList<>();

    // Getters and Setters
}
