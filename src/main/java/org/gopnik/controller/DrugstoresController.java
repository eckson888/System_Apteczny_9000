package org.gopnik.controller;

import org.gopnik.model.Drugstore;
import org.gopnik.service.DrugstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/drugstores")
public class DrugstoresController {
    @Autowired
    private DrugstoreService drugstoreService;

    @GetMapping("")
    public String drugstores(Model model) {
        model.addAttribute("drugstoreList", drugstoreService.fetchDrugstoreList());
        return "drugstores";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("drugstore", new Drugstore());
        return "drugstore-form";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Drugstore drugstore) {
        this.drugstoreService.save(drugstore);
        return "redirect:/drugstores";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam Long id) {
        drugstoreService.delete(id);
        return "redirect:/drugstores";
    }
}
