package org.gopnik.repository;

import org.gopnik.model.DrugstoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugstoreItemRepository extends JpaRepository<DrugstoreItem, Long> {
    @Query("SELECT i FROM DrugstoreItem i WHERE i.drugstore.id = :drugstoreId")
    List<DrugstoreItem> getDrugstoreInventory(@Param("drugstoreId") Long drugstoreId);
}