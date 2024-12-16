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
import org.springframework.data.domain.Page;
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
    public String main(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {

        Employee currentEmployee = employeeService.getCurrentEmployee();
        Long currentDrugstoreId = employeeService.getCurrentDrugstoreId();
        List<DrugstoreItem> items = drugstoreItemService.getAllExcludingCurrentDrugstoreId(currentDrugstoreId, page, size);
        int totalItems = drugstoreItemService.countAllExcludingCurrentDrugstoreId(currentDrugstoreId);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // Add attributes to the model
        model.addAttribute("allDrugstoreItems", items); // Items for the current page
        model.addAttribute("currentPage", page); // Current page
        model.addAttribute("totalPages", totalPages); // Total number of pages
        model.addAttribute("size", size); // Page size
        model.addAttribute("isSearch", 0); // TO MA WIEKSZY SENS NIZ SIE MOZE WYDAWAC
        //TODO: jebany optional zostawic to tak jak jest albo to zrobic jakos sensownie
        model.addAttribute("currentAddress", drugstoreService.getById(currentDrugstoreId).orElseThrow().getFullAddress());

        return "global-inventory";
    }


    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        model.addAttribute("keyword", keyword);
        List<DrugstoreItem> items;
        int totalItems;
        if (keyword.length() > 2) {
            items = drugstoreItemService.getItemsByKeywordExcludingCurrentDrugstoreId(keyword, employeeService.getCurrentDrugstoreId(), page, size);
            totalItems = drugstoreItemService.countByKeywordExcludingCurrentDrugstoreId(keyword, employeeService.getCurrentDrugstoreId());
            //
        } else {
            items = drugstoreItemService.getAllExcludingCurrentDrugstoreId(employeeService.getCurrentDrugstoreId(), page, size);
            totalItems = drugstoreItemService.countAllExcludingCurrentDrugstoreId(employeeService.getCurrentDrugstoreId());
        }
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("allDrugstoreItems", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("isSearch", 1);

        Long currentDrugstoreId = employeeService.getCurrentDrugstoreId();
        //TODO: tu również optional
        model.addAttribute("currentAddress", drugstoreService.getById(currentDrugstoreId).orElseThrow().getFullAddress());

        return "global-inventory";
    }

    @GetMapping(path = "/closest-drugstore/{id}")
    @ResponseBody
    public List<String> findClosestAndCheapestDrugstore(@PathVariable Long id, Model model) {

        Long currentDrugstoreID = employeeService.getCurrentDrugstoreId();
        String currentDrugstoreAddress = drugstoreService.getDrugstoreAddressById(currentDrugstoreID);

        List<Drugstore> drugstores = drugstoreItemService.getDrugstoresByDrugstoreItemId(id, currentDrugstoreID);
        List<String> result = new ArrayList<>();

        result.add(googleMapsService.findClosestDrugstore(currentDrugstoreAddress, drugstores));

        List<DrugstoreItem> cheapestList = drugstoreItemService.getDrugstoreItemsByDrug(drugstoreItemService.getDrugstoreItemById(id), currentDrugstoreID);

        DrugstoreItem cheapestItem = cheapestList.get(0);
        for (DrugstoreItem d : cheapestList) {
            if (d.getPrice().compareTo(cheapestItem.getPrice()) < 0) {
                cheapestItem = d;
            }
        }

        String cheapestItemString = cheapestItem.getDrugstore().getFullAddress() + ". Cena: " + cheapestItem.getPrice().toString() + "zł.";
        result.add(cheapestItemString);
        model.addAttribute("closestDrugstore", result);
        return result;
    }


}
