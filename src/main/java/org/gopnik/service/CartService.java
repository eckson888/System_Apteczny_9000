package org.gopnik.service;

import jakarta.transaction.Transactional;
import org.gopnik.model.Cart;
import org.gopnik.model.CartItem;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.repository.CartRepository;
import org.gopnik.repository.DrugstoreItemRepository;
import org.gopnik.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DrugstoreItemRepository drugstoreItemRepository;
    @Autowired
    private DrugstoreItemService drugstoreItemService;
    @Transactional
    public Cart getCart(){
        //System.out.println("DEBUG:: "+ employeeService.getCurrentEmployee().getUsername());
        return employeeService.getCurrentEmployee().getCart();

    }
    @Transactional
    public Cart addToCart(Long itemId, int quantity){
        Cart cart = getCart();
        DrugstoreItem item = drugstoreItemRepository.getById(itemId).orElseThrow(()-> new RuntimeException("item not found"));
        cart.addItem(item, quantity);
        return cartRepository.save(cart);
    }
//    @Transactional
//    public Cart saveCart(Cart cart){
//        return cartRepository.save(cart);
//    }
    @Transactional
    public Cart removeFromCart(Long itemId){
        Cart cart = getCart();
        DrugstoreItem item = drugstoreItemRepository.getById(itemId).orElseThrow(()-> new RuntimeException("item not found"));
        //cart.removeItem(item);
        return cartRepository.save(cart);
    }
}
