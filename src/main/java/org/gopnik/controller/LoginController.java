package org.gopnik.controller;


import org.gopnik.model.Employee;
import org.gopnik.service.DrugstoreService;
import org.gopnik.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DrugstoreService drugstoreService;

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "message", required = false) String message, Model model) {
        if (message != null) {
            model.addAttribute("message", message);
        }
        if (error != null) {
            model.addAttribute("errorMessage", "Nieprawidłowa nazwa użytkownika lub hasło!");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Pomyślnie wylogowano");
        }
        return "login";
    }

    @RequestMapping(path = "/login/drugstore", method = RequestMethod.GET)
    public String drugstore_get(Model model) {

        model.addAttribute("drugstore_id", new String());
        return "login-drugstore";
    }

    @PostMapping("/login/drugstore")
    public String drugstore_post(@RequestParam Long drugstore_id) {
        System.out.println(drugstore_id);
        if (drugstoreService.checkById(drugstore_id))
        {
            Employee currentEmpl = employeeService.getCurrentEmployee();
            if (!drugstore_id.equals(currentEmpl.getDrugstoreId()))
            {
                currentEmpl.setDrugstoreId(drugstore_id);
                employeeService.save(currentEmpl);
            }
            return "redirect:/main";
        } else {
            return "redirect:/login/drugstore";
        }
    }
}
