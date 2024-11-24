package org.gopnik.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@Table(name="cart_item")
@Setter
@Getter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    private DrugstoreItem drugstoreItem;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;


    public CartItem(Cart cart, DrugstoreItem item, int quantity) {
        this.cart=cart;
        this.drugstoreItem=item;
        this.quantity=quantity;
    }
    public CartItem(){

    }
}
