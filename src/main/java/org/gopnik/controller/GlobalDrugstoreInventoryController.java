package org.gopnik.controller;

import org.gopnik.model.DrugstoreItem;
import org.gopnik.model.Employee;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.DrugstoreService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        model.addAttribute("allDrugstoreItems",drugstoreItemService.getAll());
        return "global-inventory";
    }


    @RequestMapping(path = "/search",method= RequestMethod.GET)
    public String search(@RequestParam String keyword, Model model)
    {
        if (keyword.length()>2) {
            List<DrugstoreItem> list = drugstoreItemService.getByKeyword(keyword);
            model.addAttribute("allDrugstoreItems", list);
            model.addAttribute("keyword",keyword);
        } else {
            List<DrugstoreItem> list = drugstoreItemService.getAll();
            model.addAttribute("allDrugstoreItems", list);  // TODO: wyjebac pokazywanie lekow z current apteki w global inventory
        }
        return "/global-inventory";
    }
}
