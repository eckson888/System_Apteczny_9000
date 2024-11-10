package org.gopnik.repository;

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

    List<DrugstoreItem> findByKeywordInAllDrugstores(String keyword);

    List<DrugstoreItem> getAll();


    Optional<DrugstoreItem> getById(Long id);
}