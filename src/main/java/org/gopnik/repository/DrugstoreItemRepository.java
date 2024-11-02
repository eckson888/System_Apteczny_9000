package org.gopnik.repository;

import org.gopnik.model.DrugstoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugstoreItemRepository extends JpaRepository<DrugstoreItem, Long> {

}
