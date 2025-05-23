package org.gopnik.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.Objects;

@ToString
@Entity
@Getter
@Setter
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

    public String getDrugPharmaceuticalForm() {
        return drug.getPharmaceuticalForm();
    }

    public String getDrugActiveSubstance() {
        return drug.getActiveSubstance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugstoreItem that = (DrugstoreItem) o;
        boolean bigDecimalBoolean = (getPrice().compareTo(that.getPrice())) == 0;
        return Objects.equals(getDrugstore().getId(), that.getDrugstore().getId()) && Objects.equals(getDrug().getId(), that.getDrug().getId()) && bigDecimalBoolean;
    }

    public Drugstore getDrugstore() {
        return drugstore;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    @Override
    public String toString() {
        return String.format("id:%s name:\"%s\" quantity:%s price:%sPLN", getId(), getDrugName(), getQuantity(), getPrice());
    }
}