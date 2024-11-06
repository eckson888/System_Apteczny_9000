package org.gopnik.repository;

import org.gopnik.model.DrugstoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDrugstoreItemInterface extends JpaRepository<DrugstoreItem, Long> {
}
