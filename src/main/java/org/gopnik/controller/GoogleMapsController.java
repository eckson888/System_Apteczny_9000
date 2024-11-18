//package org.gopnik.controller;
//
//import com.google.maps.model.GeocodingResult;
//import org.gopnik.model.Drugstore;
//import org.gopnik.repository.DrugstoreItemRepository;
//import org.gopnik.repository.IDrugstoreDatabase;
//import org.gopnik.service.EmployeeService;
//import org.gopnik.service.GoogleMapsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class GoogleMapsController {
//
//    @Autowired
//    private GoogleMapsService googleMapsService;
//
//    @Autowired
//    private IDrugstoreDatabase drugstoreRepository;
//    @Autowired
//    private DrugstoreItemRepository drugstoreItemRepository;
//    @Autowired
//    private EmployeeService employeeService;
//
//    @RequestMapping(path = "/closest-drugstore/{id}", method = RequestMethod.POST)
//    public String findClosestDrugstore(@PathVariable Long id, Model model)
//    {
//        Long currentDrugstoreID = employeeService.getCurrentEmployee().getDrugstoreId();
//        List<Drugstore> drugstores = drugstoreItemRepository.getDrugstoresByDrugstoreItemId(id,currentDrugstoreID);
//
//        String currentDrugstoreAddress = drugstoreRepository.findById(currentDrugstoreID).get().getAddress();
//
//        String result = googleMapsService.findClosestDrugstore(currentDrugstoreAddress, drugstores); //TODO tutaj te drugstores to nie po wszystkich tylko po tych co maja leka
//        model.addAttribute("closest-drugstore",result);
//        return "redirect:/global-inventory";
//    }
//}
//
