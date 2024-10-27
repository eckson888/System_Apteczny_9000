package org.gopnik.service;

import org.gopnik.model.Drugstore;
import org.gopnik.repository.IDrugstoreDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugstoreService implements IDrugstoreService {

    @Autowired
    private IDrugstoreDatabase drugstoreRepository;

    @Override
    public Drugstore save(Drugstore drugstore) {
        return drugstoreRepository.save(drugstore);
    }

    @Override
    public List<Drugstore> fetchDrugstoreList() {
        return (List<Drugstore>) drugstoreRepository.findAll();
    }
}
