package org.gopnik.repository;

import org.gopnik.model.Drug;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IGeneralDrugDatabase {
    public Optional<Drug> getByName(String name);
    public List<Drug> getAll();
    public Optional<Drug> getById(Long id);
}
