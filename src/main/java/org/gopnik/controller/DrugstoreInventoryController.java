package org.gopnik.controller;

import lombok.extern.slf4j.Slf4j;
import org.gopnik.model.*;
import org.gopnik.repository.GeneralDrugDatabase;
import org.gopnik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(path = "/drugstore-inventory")
public class DrugstoreInventoryController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DrugstoreItemService drugstoreItemService;

    @Autowired
    private DrugService drugService;
    @Autowired
    private DrugstoreService drugstoreService;
    @Autowired
    private CartService cartService;


    @GetMapping("")
    public String main(Model model) {
        Employee currentEmployee = employeeService.getCurrentEmployee();
        model.addAttribute("employeeInfo", currentEmployee.toString());
        model.addAttribute("drugstoreInventory", drugstoreItemService.getDrugstoreItems(currentEmployee.getDrugstoreId()));
        return "drugstore-inventory";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("drugstoreItem", new DrugstoreItem());
        return "drug-form";
    }

    @RequestMapping(path = "/add/search", method = RequestMethod.POST)
    public String addItem(@ModelAttribute DrugstoreItem drugstoreItem, @RequestParam("drugId") int id) {

        drugstoreItem.setDrugstore(drugstoreService.getById(employeeService.getCurrentEmployee().getDrugstoreId()).orElseThrow());
        drugstoreItem.setDrug(drugService.findById(id));
        // System.out.println(item.getDrugstore().getId() + "tutaj");
        drugstoreItemService.addDrugstoreItem(drugstoreItem);
        return "drug-form";
    }

    @RequestMapping(path = "/add/search", method = RequestMethod.GET)
    public String search_add(@RequestParam String keyword, Model model) {
        model.addAttribute("drugstoreItem", new DrugstoreItem());

        if (keyword != null) {
            List<Drug> list = drugService.getByKeyword(keyword);
            model.addAttribute("drugs", list);
            model.addAttribute("keyword", keyword);
        }
        return "drug-form";
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(@RequestParam String keyword, Model model) {
        if (keyword.length() > 2) {
//            List<String> keywords = List.of(keyword.split("\\s+"));
            List<DrugstoreItem> list = drugstoreItemService.getByKeywordInSomeDrugstore(keyword, employeeService.getCurrentEmployee().getDrugstoreId()); // xdd
            model.addAttribute("drugstoreInventory", list);
            model.addAttribute("keyword", keyword);
        } else {
            List<DrugstoreItem> list = drugstoreItemService.getDrugstoreItems(employeeService.getCurrentEmployee().getDrugstoreId());
            model.addAttribute("drugstoreInventory", list);
        }
        return "/drugstore-inventory";
    }

    @RequestMapping(path = "/buy/{itemId}/{quantity}", method = RequestMethod.GET)
    public String addToCart(@PathVariable Long itemId, @PathVariable int quantity) {
        cartService.addToCart(itemId, quantity);
        //System.out.println(employeeService.getCurrentEmployee().getCart().getItems().getFirst().getQuantity());
        System.out.println(employeeService.getCurrentEmployee().getCart().getItems());

        return "redirect:/drugstore-inventory";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        System.out.println(cart.getItems());
        model.addAttribute("cart", cart);
        model.addAttribute("cartPrice", cart.getCartSum().toString());
        return "cart";
    }

    @RequestMapping(path = "/cart/remove/{drugstoreItemId}", method = RequestMethod.GET)
    public String removeFromCart(@PathVariable Long drugstoreItemId) {
        log.info("DrugItemId pobiernay z htmla: " + drugstoreItemId);
        cartService.removeFromCart(drugstoreItemId);
        return "redirect:/drugstore-inventory/cart";
    }

    @RequestMapping(path = "/cart/sell", method = RequestMethod.GET)
    public String sellAllItems() {
        cartService.sellAllItems();
        return "redirect:/drugstore-inventory/cart";
    }

}
