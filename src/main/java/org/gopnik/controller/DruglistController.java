package org.gopnik.controller;

import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drugs")
public class DruglistController {
    @Autowired
    private DrugstoreItemService drugstoreItemService;

    @GetMapping("")
    public String drugs(Model model) {
        model.addAttribute("drugitemList", drugstoreItemService.fetchDrugsFromDatabase());
        return "druglist";
    }
}
