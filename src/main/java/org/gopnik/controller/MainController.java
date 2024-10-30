package org.gopnik.controller;

import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/main")
    public String main(Model model) {
        String employeeInfo = employeeService.getCurrentEmployee().toString();
        model.addAttribute("employeeInfo", employeeInfo);
        return "main";
    }
}
