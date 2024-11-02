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

    public String getDrugName() {
        return drug.getName();
    }

    public String getDrugCommonName() {
        return drug.getCommonName();
    }

    public String getDrugPharmaceuticalForm(){
        return drug.getPharmaceuticalForm();
    }

    public String getDrugActiveSubstance(){
        return drug.getActiveSubstance();
    }


}