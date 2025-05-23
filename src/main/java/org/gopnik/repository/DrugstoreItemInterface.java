package org.gopnik.repository;

import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugstoreItemInterface {

    @Query("SELECT i FROM DrugstoreItem i WHERE i.drugstore.id = :drugstoreId")
    List<DrugstoreItem> getDrugstoreInventory(@Param("drugstoreId") Long drugstoreId);

    List<DrugstoreItem> findByKeywordInSomeDrugstore(String keyword, Long id);

    List<DrugstoreItem> getItemsByKeywordExcludingCurrentDrugstoreId(String keyword, Long drugstoreId, int page, int size);

    List<DrugstoreItem> findByKeywordInAllDrugstores(String keyword);

    List<DrugstoreItem> findByIdInGivenDrugstore(int drugid, Long drugstoreid);

    List<DrugstoreItem> getAll();


    List<Drugstore> getDrugstoresByDrugstoreItemId(Long drugstoreItemId,Long currentDrugstoreId);

    Optional<DrugstoreItem> getById(Long id);

    List<DrugstoreItem> getDrugstoreItemsByDrug(DrugstoreItem drugstoreItem, Long drugstoreId);

    List<DrugstoreItem> getPagedItemsByKeywordAndCurrentDrugstoreId(String keyword, Long drugstoreId, int page, int size);
}