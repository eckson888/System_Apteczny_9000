package org.gopnik.repository;

import org.gopnik.model.Drugstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugstoreDatabase extends JpaRepository<Drugstore, Long> {
}
