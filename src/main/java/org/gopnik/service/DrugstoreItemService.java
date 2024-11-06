package org.gopnik.service;

import org.gopnik.model.DrugstoreItem;
import org.gopnik.repository.DrugstoreItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugstoreItemService {
    @Autowired
    private DrugstoreItemRepository drugstoreItemRepository;

    public List<DrugstoreItem> fetchDrugstoreItemList() {
        return (List<DrugstoreItem>) drugstoreItemRepository.findAll();
    }

    public List<DrugstoreItem> getDrugstoreItems(Long id) {
        return drugstoreItemRepository.getDrugstoreInventory(id);
    }
    public void addDrugstoreItem(DrugstoreItem item){
        drugstoreItemRepository.save(item);
    }
}