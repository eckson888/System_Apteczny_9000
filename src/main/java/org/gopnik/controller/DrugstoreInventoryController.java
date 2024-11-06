package org.gopnik.controller;

import org.gopnik.model.Drug;
import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.model.Employee;
import org.gopnik.repository.GeneralDrugDatabase;
import org.gopnik.service.DrugService;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.DrugstoreService;
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
    private DrugstoreService drugstoreService;


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
    @RequestMapping(path = "/add/search", method = RequestMethod.POST)
    public String addItem(@ModelAttribute DrugstoreItem drugstoreItem, @RequestParam("id") int id){

        drugstoreItem.setDrugstore(drugstoreService.getById(employeeService.getCurrentEmployee().getDrugstoreId()).orElseThrow());
        drugstoreItem.setDrug(drugService.findById(id));
       // System.out.println(item.getDrugstore().getId() + "tutaj");
        drugstoreItemService.addDrugstoreItem(drugstoreItem);
        return "drug-form";
    }

    @RequestMapping(path = "/add/search",method=RequestMethod.GET)
    public String search(@RequestParam String keyword, Model model)
    {
        model.addAttribute("drugstoreItem", new DrugstoreItem());

        if (keyword != null) {
            List<Drug> list = drugService.getByKeyword(keyword);
            model.addAttribute("drugs", list);
            model.addAttribute("keyword",keyword);
        }
        return "drug-form";
    }



}
