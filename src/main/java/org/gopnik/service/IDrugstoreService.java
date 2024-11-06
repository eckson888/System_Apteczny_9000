package org.gopnik.service;

import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;

import java.util.List;

public interface IDrugstoreService {
    public Drugstore save(Drugstore drugstore);
    public List<Drugstore> fetchDrugstoreList();

}
