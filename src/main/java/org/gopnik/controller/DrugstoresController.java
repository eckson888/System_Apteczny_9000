package org.gopnik.controller;

import org.gopnik.model.Drugstore;
import org.gopnik.service.DrugstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DrugstoresController {
    @Autowired
    private DrugstoreService drugstoreService;


    @GetMapping("/drugstores")
    public List<Drugstore> fetchDrugstoresList() {
        return drugstoreService.fetchDrugstoreList();
    }
}
