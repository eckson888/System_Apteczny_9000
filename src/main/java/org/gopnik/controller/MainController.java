package org.gopnik.controller;

import org.gopnik.model.Employee;
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
    DrugstoreItemService drugstoreItemService;

    @GetMapping("/main")
    public String main(Model model) {
        Employee currentEmployee = employeeService.getCurrentEmployee();
        Long employeeDrugstore = (Long) model.getAttribute("chosenDrugstore");
//        currentEmployee.setDrugstoreId(employeeDrugstore);
        model.addAttribute("employeeInfo", currentEmployee.toString());
//        model.addAttribute("drugstoreInventory", drugstoreItemService.getDrugstoreItems(currentEmployee.getDrugstoreId()));

        return "main";
    }
}
