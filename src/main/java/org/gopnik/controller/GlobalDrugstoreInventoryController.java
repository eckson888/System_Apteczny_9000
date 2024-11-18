package org.gopnik.controller;

import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.model.Employee;
import org.gopnik.repository.DrugstoreItemRepository;
import org.gopnik.repository.IDrugstoreDatabase;
import org.gopnik.service.DrugstoreItemService;
import org.gopnik.service.DrugstoreService;
import org.gopnik.service.EmployeeService;
import org.gopnik.service.GoogleMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private IDrugstoreDatabase drugstoreRepository;
    @Autowired
    private DrugstoreItemRepository drugstoreItemRepository;    //TODO zrobic te metody w serwisach



    @GetMapping("")
    public String main(Model model) {

        Employee currentEmployee = employeeService.getCurrentEmployee();
//        model.addAttribute("employeeInfo", currentEmployee.toString());
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
        return "global-inventory";
    }

    @RequestMapping(path = "/closest-drugstore/{id}", method = RequestMethod.GET)
    public String findClosestDrugstore(@PathVariable Long id, Model model)
    {
        Long currentDrugstoreID = employeeService.getCurrentEmployee().getDrugstoreId();
        List<Drugstore> drugstores = drugstoreItemRepository.getDrugstoresByDrugstoreItemId(id,currentDrugstoreID);

        String currentDrugstoreAddress = drugstoreRepository.findById(currentDrugstoreID).get().getAddress();   //TODO zrobic te metody w serwisach

        String result = googleMapsService.findClosestDrugstore(currentDrugstoreAddress, drugstores);
        model.addAttribute("closestDrugstore",result);
        return "global-inventory";
    }
}
