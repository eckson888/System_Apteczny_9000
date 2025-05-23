package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "cart")

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    @OneToOne(mappedBy = "cart")
    private Employee employee;

    public void addItem(DrugstoreItem item, int quantity) {
        for (CartItem i : items) {
            if (i.getDrugstoreItem() == item) {
                i.setQuantity(i.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(this, item, quantity));
    }

    public void removeItem(DrugstoreItem item, int quantityToSell) {
        for (CartItem i : items) {
            if (i.getDrugstoreItem() == item) {
                if (i.getQuantity() > quantityToSell) {
                    i.setQuantity((i.getQuantity() - quantityToSell));
                    return;
                } else {
                    items.remove(i);
                    return;
                }

            }

        }
    }

    public BigDecimal getCartSum() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (CartItem i : this.items) {
            sum = sum.add(i.getDrugstoreItem().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
        }
        return sum;
    }
}
