package org.gopnik.controller;

import org.gopnik.model.Employee;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.DrugstoreService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/global-inventory")
public class GlobalDrugstoreInventoryController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DrugstoreItemService drugstoreItemService;
    @Autowired
    private DrugstoreService drugstoreService;


    @GetMapping("")
    public String main(Model model) {

        // TODO: POZBYC SIE NIEPOTRZEBNYCH RZECZY
        Employee currentEmployee = employeeService.getCurrentEmployee();
        model.addAttribute("employeeInfo", currentEmployee.toString());
        model.addAttribute("drugstoreInventory", drugstoreItemService.getDrugstoreItems(currentEmployee.getDrugstoreId()));
        model.addAttribute("allDrugstores", drugstoreService.fetchDrugstoreList());
        return "global-inventory";
    }
}
