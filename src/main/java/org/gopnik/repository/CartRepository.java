package org.gopnik.repository;

import org.gopnik.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{


    //Cart save(Cart cart);
}
