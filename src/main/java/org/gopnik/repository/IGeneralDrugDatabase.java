package org.gopnik.repository;

import org.gopnik.model.Drug;

import java.util.List;
import java.util.Optional;

public interface IGeneralDrugDatabase {
    public Optional<Drug> getByName(String name);

    Optional<List<String>> get100ByAll(String name);

    public List<Drug> getAll();

}
