package org.gopnik.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.gopnik.model.Cart;
import org.gopnik.model.CartItem;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.repository.CartRepository;
import org.gopnik.repository.DrugstoreItemRepository;
import org.gopnik.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
        log.info("!! wchodze do koszyka !!"); //wazne
        return employeeService.getCurrentEmployee().getCart();
    }
    @Transactional
    public Cart addToCart(Long itemId, int quantity){
        Cart cart = getCart();
        DrugstoreItem item = drugstoreItemRepository.getById(itemId).orElseThrow(()-> new RuntimeException("item not found"));
        cart.addItem(item, quantity);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(Long itemId){
        Cart cart = getCart();
        DrugstoreItem item = drugstoreItemRepository.getById(itemId).orElseThrow(()-> new RuntimeException("item not found"));
        cart.removeItem(item,1);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart sellAllItems(){
        Cart cart = getCart();
        DrugstoreItem toBeSold;
        Integer quantitiesToSell;
        int initial_size = cart.getItems().size();

        for(int i = 0;i<initial_size;i++)
        {
            toBeSold = (cart.getItems().get(0).getDrugstoreItem());
            quantitiesToSell = (cart.getItems().get(0).getQuantity());
            log.info(String.valueOf(quantitiesToSell));
            cart.removeItem(toBeSold,quantitiesToSell);
            drugstoreItemService.removeDrugstoreItem(toBeSold, quantitiesToSell);                           //TODO zrobic ze jak leci logout usera to jego koszyk sie czysci
        }                                                                                                   //albo cos zeby sie nie nakladaly te koszyki z roznych aptek nwm
        return cartRepository.save(cart);
    }

}
