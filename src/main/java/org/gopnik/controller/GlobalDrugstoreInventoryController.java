package org.gopnik.controller;

import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.model.Employee;
import org.gopnik.repository.GeneralDrugDatabase;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.DrugstoreService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        Employee currentEmployee = employeeService.getCurrentEmployee();
        model.addAttribute("employeeInfo", currentEmployee.toString());
        model.addAttribute("drugstoreInventory", drugstoreItemService.getDrugstoreItems(currentEmployee.getDrugstoreId()));
        model.addAttribute("allDrugstores",drugstoreService.fetchDrugstoreList());
        return "global-inventory";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("drugstoreItem", new DrugstoreItem());
        //model.addAttribute("drugList",generalDrugDatabase.getAll());
        return "drug-form";
    }


}
