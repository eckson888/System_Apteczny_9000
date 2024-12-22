package org.gopnik.controller;

import org.gopnik.model.Drugstore;
import org.gopnik.model.Employee;
import org.gopnik.service.DrugService;
import org.gopnik.service.DrugstoreService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO: TO MOZE ZMIENIC NA ADMINPANEL ZAMIAST DRUGSTORES
@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoresController {
    @Autowired
    private DrugstoreService drugstoreService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String drugstores(Model model) {
        model.addAttribute("drugstoreList", drugstoreService.fetchDrugstoreList());
        return "drugstores";
    }

    // TU POZMIENIAC NA NP add-drugstore ?
    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("drugstore", new Drugstore());
        return "drugstore-form";
    }

    // TU POZMIENIAC NA NP add-drugstore ?
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Drugstore drugstore) {
        this.drugstoreService.save(drugstore);
        return "redirect:/drugstores";
    }

    // TU POZMIENIAC NA NP add-drugstore ?
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam Long id) {
        drugstoreService.delete(id);
        return "redirect:/drugstores";
    }

    @RequestMapping(path = "/add-employee", method = RequestMethod.GET)
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @RequestMapping(path = "/add-employee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.registerEmployee(employee);
        return "redirect:/drugstores";
    }
}
