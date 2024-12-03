package org.gopnik.controller;

import org.gopnik.model.Cart;
import org.gopnik.model.Employee;
import org.gopnik.service.CartService;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.DrugstoreService;
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
    DrugstoreService drugstoreService;
//    @Autowired
//    CartService cartService;
//    @Autowired
//    DrugstoreItemService drugstoreItemService;    nwm po co to tu jest wiec koment

    @GetMapping("/main")
    public String main(Model model) {
        Employee currentEmployee = employeeService.getCurrentEmployee();
        Long employeeDrugstore = (Long) model.getAttribute("chosenDrugstore");
        model.addAttribute("employeeName", currentEmployee.getUsername());
        model.addAttribute("employeeDrugstore",drugstoreService.getDrugstoreAddressById(currentEmployee.getDrugstoreId()));
        return "main";
    }

    @GetMapping("")
    public String mainRedirect() {
        return "redirect:/main";
    }

}
