package org.gopnik.controller;

import lombok.extern.slf4j.Slf4j;
import org.gopnik.model.*;
import org.gopnik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String main(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,  Model model) {

        Employee currentEmployee = employeeService.getCurrentEmployee();
        List<DrugstoreItem> items = drugstoreItemService.getAllPagedDrugstoreItems(employeeService.getCurrentDrugstoreId(), page, size);
        int totalItems = drugstoreItemService.countAllByCurrentDrugstoreId(currentEmployee.getDrugstoreId());
        int totalPages = (int) Math.ceil((double) totalItems / size);

        model.addAttribute("employeeInfo", currentEmployee.toString());
        model.addAttribute("drugstoreInventory", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("isSearch", 0); // TO MA WIEKSZY SENS NIZ SIE MOZE WYDAWAC

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
    public String search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,                                    //TODO zrobic paginacje gdy jest duzo itemow po szukaniu keywordem
            @RequestParam(defaultValue = "10") int size,                                   //EDIT: zrobilem ale nie testowalem bo nie ma tyle wpisuw zeby wgl byly strony
            Model model){                                                                  // zakladam ze dziala
        model.addAttribute("keyword", keyword);
        List<DrugstoreItem> items;
        int totalItems;
        if (keyword.length() > 2) {
            items = drugstoreItemService.getPagedItemsByKeywordAndCurrentDrugstoreId(keyword, employeeService.getCurrentDrugstoreId(), page, size);
            totalItems = drugstoreItemService.countByKeywordAndCurrentDrugstoreId(keyword, employeeService.getCurrentDrugstoreId());

        } else {
            items = drugstoreItemService.getAllPagedDrugstoreItems(employeeService.getCurrentDrugstoreId(), page, size);
            totalItems = drugstoreItemService.countAllByCurrentDrugstoreId(employeeService.getCurrentDrugstoreId());
        }
        System.out.println(items);
        System.out.println(totalItems);

        int totalPages = (int) Math.ceil ((double) totalItems / size);
        model.addAttribute("drugstoreInventory", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("isSearch", 1);
        return "drugstore-inventory";
    }

    @RequestMapping(path = "/buy/{itemId}/{quantity}", method = RequestMethod.GET)
    public String addToCart(@PathVariable Long itemId, @PathVariable int quantity) {
        cartService.addToCart(itemId, quantity);

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
