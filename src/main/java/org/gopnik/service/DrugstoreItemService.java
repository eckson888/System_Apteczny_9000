package org.gopnik.service;

import jakarta.transaction.Transactional;
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
        return (List<DrugstoreItem>) drugstoreItemRepository.getAll();
    }


    @Transactional
    public List<DrugstoreItem> getByKeyword(String keyword) {

        return this.drugstoreItemRepository.findByKeywordInAllDrugstores(keyword);
    }

    @Transactional
    public List<DrugstoreItem> getByKeywordInSomeDrugstore(String keyword, Long id) {

        return this.drugstoreItemRepository.findByKeywordInSomeDrugstore(keyword,id);
    }



    public List<DrugstoreItem> getDrugstoreItems(Long id) {
        return drugstoreItemRepository.getDrugstoreInventory(id);
    }

    public void addDrugstoreItem(DrugstoreItem item){
        drugstoreItemRepository.save(item);
    }

    public List<DrugstoreItem> getAll()
    {
        return this.drugstoreItemRepository.getAll();
    }
}