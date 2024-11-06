package org.gopnik.repository;

import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDrugstoreDatabase extends JpaRepository<Drugstore, Long> {

}
