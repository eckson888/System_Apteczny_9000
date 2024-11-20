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

import java.math.BigDecimal;
import java.util.ArrayList;
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


    @GetMapping("")
    public String main(Model model) {

        Employee currentEmployee = employeeService.getCurrentEmployee();
//        model.addAttribute("employeeInfo", currentEmployee.toString());
        model.addAttribute("allDrugstoreItems",drugstoreItemService.getAllExcludingCurrentDrugstoreId(employeeService.getCurrentDrugstoreId()));
        return "global-inventory";
    }


    @RequestMapping(path = "/search",method= RequestMethod.GET)
    public String search(@RequestParam String keyword, Model model)
    {
        if (keyword.length()>2) {
            List<DrugstoreItem> list = drugstoreItemService.getItemsByKeywordExcludingCurrentDrugstoreId(keyword, employeeService.getCurrentDrugstoreId());
            model.addAttribute("allDrugstoreItems", list);
            model.addAttribute("keyword",keyword);
        } else {
            List<DrugstoreItem> list = drugstoreItemService.getAllExcludingCurrentDrugstoreId(employeeService.getCurrentDrugstoreId());
            model.addAttribute("allDrugstoreItems", list);
        }
        return "global-inventory";
    }

    @GetMapping(path = "/closest-drugstore/{id}")
    @ResponseBody
    public List<String> findClosestAndCheapestDrugstore(@PathVariable Long id, Model model)
    {

        Long currentDrugstoreID = employeeService.getCurrentDrugstoreId();
        String currentDrugstoreAddress = drugstoreService.getDrugstoreAddressById(currentDrugstoreID);

        List<Drugstore> drugstores = drugstoreItemService.getDrugstoresByDrugstoreItemId(id, currentDrugstoreID);
        List<String> result = new ArrayList<>();

        result.add(googleMapsService.findClosestDrugstore(currentDrugstoreAddress, drugstores));

        List<DrugstoreItem> cheapestList = drugstoreItemService.getDrugstoreItemsByDrug(drugstoreItemService.getDrugstoreItemById(id),currentDrugstoreID);

        DrugstoreItem cheapestItem = cheapestList.get(0);
        for(DrugstoreItem d: cheapestList)
        {
            if(d.getPrice().compareTo(cheapestItem.getPrice()) <0)
            {
                cheapestItem=d;
            }
        }

        String cheapestItemString = cheapestItem.getDrugstore().getFullAddress() +". Cena: " + cheapestItem.getPrice().toString()+"zł";
        result.add(cheapestItemString);
        model.addAttribute("closestDrugstore",result);
        return result;
    }


}
