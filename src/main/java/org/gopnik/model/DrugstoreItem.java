package org.gopnik.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "drugstore_inventory")
public class DrugstoreItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drugstore_id", nullable = false)
    private Drugstore drugstore;

    @ManyToOne
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug; //loool

    private BigDecimal price;
    private int quantity;

    // Constructors, Getters, Setters
}