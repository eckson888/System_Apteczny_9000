package org.gopnik.service;

import jakarta.transaction.Transactional;
import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.repository.DrugstoreItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugstoreItemService {
    @Autowired
    private DrugstoreItemRepository drugstoreItemRepository;
    @Autowired
    private EventLogService eventLogService;

    public List<DrugstoreItem> fetchDrugstoreItemList() {
        return (List<DrugstoreItem>) drugstoreItemRepository.getAll();
    }

    @Transactional
    public List<DrugstoreItem> getByKeyword(String keyword) {

        return this.drugstoreItemRepository.findByKeywordInAllDrugstores(keyword);
    }

    @Transactional
    public List<DrugstoreItem> getByKeywordInSomeDrugstore(String keyword, Long id) {

        return this.drugstoreItemRepository.findByKeywordInSomeDrugstore(keyword, id);
    }

    @Transactional
    public List<DrugstoreItem> getItemsByKeywordExcludingCurrentDrugstoreId(String keyword, Long drugstoreId, int page, int size) {
        return drugstoreItemRepository.getItemsByKeywordExcludingCurrentDrugstoreId(keyword, drugstoreId, page, size);
    }

    public DrugstoreItem getDrugstoreItemById(Long id) {

        return this.drugstoreItemRepository.getById(id).orElse(null);
    }

    public List<DrugstoreItem> getDrugstoreItems(Long id) {
        return drugstoreItemRepository.getDrugstoreInventory(id);
    }

    public void addDrugstoreItem(DrugstoreItem item) {
        List<DrugstoreItem> sameDrugs = drugstoreItemRepository.findByIdInGivenDrugstore(item.getDrug().getId(), item.getDrugstore().getId());

        for (int i = 0; i < sameDrugs.size(); i++) {
            DrugstoreItem listCurrent = sameDrugs.get(i);
            if (item.equals(listCurrent)) {
                listCurrent.addQuantity(item.getQuantity());
                drugstoreItemRepository.save(listCurrent);
                return;
            }
        }

        drugstoreItemRepository.save(item);
        eventLogService.addEventLog("Added to inventory: {" + item.toString() + "}");
    }

    public List<DrugstoreItem> getDrugstoreItemsByDrug(DrugstoreItem drugstoreItem, Long drugstoreId) {
        return drugstoreItemRepository.getDrugstoreItemsByDrug(drugstoreItem, drugstoreId);
    }

    public List<DrugstoreItem> getAll() {
        return this.drugstoreItemRepository.getAll();
    }

    public List<DrugstoreItem> getAllExcludingCurrentDrugstoreId(Long drugstoreId, int page, int size) {
        return drugstoreItemRepository.getAllExcludingCurrentDrugstoreId(drugstoreId, page, size);
    }

    public List<Drugstore> getDrugstoresByDrugstoreItemId(Long itemId, Long currentDrugstoreId) {
        return drugstoreItemRepository.getDrugstoresByDrugstoreItemId(itemId, currentDrugstoreId);
    }

    public void removeDrugstoreItemById(Long id) {
        DrugstoreItem item = drugstoreItemRepository.getById(id).orElseThrow(() -> new RuntimeException("item not found"));
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            drugstoreItemRepository.removeItem(item);
        }
    }

    public void removeDrugstoreItem(DrugstoreItem item, int quantity) {
        if (item.getQuantity() > quantity) {
            item.setQuantity(item.getQuantity() - quantity);
            drugstoreItemRepository.save(item);
        } else {
            drugstoreItemRepository.removeItem(item);
        }

    }

    public int countByKeywordExcludingCurrentDrugstoreId(String keyword, Long currentDrugstoreId) {
        return drugstoreItemRepository.countByKeywordExcludingCurrentDrugstoreId(keyword, currentDrugstoreId);
    }

    public int countAllExcludingCurrentDrugstoreId(Long currentDrugstoreId) {
        return drugstoreItemRepository.countAllExcludingCurrentDrugstoreId(currentDrugstoreId);
    }

    public int countByKeywordAndCurrentDrugstoreId(String keyword, Long currentDrugstoreId) {
        return drugstoreItemRepository.countByKeywordAndCurrentDrugstoreId(keyword, currentDrugstoreId);
    }

    public int countAllByCurrentDrugstoreId(Long currentDrugstoreId) {
        return drugstoreItemRepository.countAllByCurrentDrugstoreId(currentDrugstoreId);
    }

    @Transactional
    public List<DrugstoreItem> getAllPagedDrugstoreItems(Long currentDrugstoreId, int page, int size) {
        return drugstoreItemRepository.getAllPagedDrugstoreItems(currentDrugstoreId, page, size);
    }

    @Transactional
    public List<DrugstoreItem> getPagedItemsByKeywordAndCurrentDrugstoreId(String keyword, Long drugstoreId, int page, int size) {
        return drugstoreItemRepository.getPagedItemsByKeywordAndCurrentDrugstoreId(keyword, drugstoreId, page, size);
    }
}