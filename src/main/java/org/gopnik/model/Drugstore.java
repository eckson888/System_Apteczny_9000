package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Getter
@Setter
@Table(name="drugstores")
public class Drugstore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank(message = "ID apteki jest wymagane")
//    private String drugstoreID;
    @NotBlank()
    private String city;
    @NotBlank()
    private String address;

    @OneToMany(mappedBy = "drugstore", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<DrugstoreItem> inventory = new ArrayList<>();

    // Getters and Setters
}
