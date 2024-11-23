package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name="cart")

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany (mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    @OneToOne(mappedBy = "cart")
    private Employee employee;

    public void addItem(DrugstoreItem item, int quantity){
        for(CartItem i : items){
            if(i.getDrugstoreItem() == item){
                i.setQuantity(i.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(this, item, quantity));
    }

    public void removeItem(DrugstoreItem item){
        for(CartItem i : items){
            if(i.getDrugstoreItem() == item){
                if(i.getQuantity()>1){
                    i.setQuantity((i.getQuantity()-1));
                }
                else items.remove(i);
            }
            return;
        }
    }
}
