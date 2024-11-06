package org.gopnik.controller;

import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.model.Employee;
import org.gopnik.repository.GeneralDrugDatabase;
import org.gopnik.service.DrugService;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/drugstore-inventory")
public class DrugstoreInventoryController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DrugstoreItemService drugstoreItemService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private GeneralDrugDatabase generalDrugDatabase;

    @GetMapping("")
    public String main(Model model) {
        Employee currentEmployee = employeeService.getCurrentEmployee();
        model.addAttribute("employeeInfo", currentEmployee.toString());
        model.addAttribute("drugstoreInventory", drugstoreItemService.getDrugstoreItems(currentEmployee.getDrugstoreId()));
        return "drugstore-inventory";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("drugstoreItem", new DrugstoreItem());   //TODO
        return "drug-form";
    }

    @GetMapping("/search")
    @ResponseBody
    public Optional<List<String>> search(@RequestParam("keyword") String keyword) {
        System.out.println("keyword:" + keyword);
        System.out.println(drugService.get100ByAll(keyword));   //< ^ debug
        return drugService.get100ByAll(keyword);
    }


}
