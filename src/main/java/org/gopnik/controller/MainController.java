package org.gopnik.controller;

import org.gopnik.model.Cart;
import org.gopnik.model.Employee;
import org.gopnik.service.CartService;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    CartService cartService;

    @Autowired
    DrugstoreItemService drugstoreItemService;

    @GetMapping("/main")
    public String main(Model model) {
        Employee currentEmployee = employeeService.getCurrentEmployee();
        Long employeeDrugstore = (Long) model.getAttribute("chosenDrugstore");
        model.addAttribute("employeeInfo", currentEmployee.toString());
        return "main";
    }
    @GetMapping("/cart")                    //TODO wyjebac to gdzies indziej dalem to tu zeby testowac
    public String getCart(Model model){
        Cart cart = cartService.getCart();
        System.out.println(cart.getItems());
        model.addAttribute("cart", cart);
        return "cart";
    }
}
