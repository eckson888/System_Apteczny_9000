package org.gopnik.controller;

import org.gopnik.model.Employee;
import org.gopnik.repository.InventoryRepository;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    EmployeeService employeeService;

    // wyswietlanie stanu robione na szybko więc jako tako
    @Autowired
    InventoryRepository inventoryRepository;    // TODO: tu pasuje zrobić serwis zamiast tego

    @GetMapping("/main")
    public String main(Model model) {
        Employee currentEmployee = employeeService.getCurrentEmployee();
        model.addAttribute("employeeInfo", currentEmployee.toString());

                                                            // najkrotszy getter na umcs
        model.addAttribute("drugstoreInventory", inventoryRepository.getDrugstoreInventory(currentEmployee.getDrugstoreId()));

        return "main";
    }
}
