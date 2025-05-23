package org.gopnik.service;

import org.gopnik.model.Drugstore;
import org.gopnik.repository.IDrugstoreDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void delete(Long id){
        drugstoreRepository.deleteById(id);
    }

    public boolean checkById(Long id)
    {
        return drugstoreRepository.existsById(id);
    }
    public Optional<Drugstore> getById(Long id){
        return drugstoreRepository.findById(id);
    }
    public String getDrugstoreAddressById(Long id) {return drugstoreRepository.findById(id).get().getAddress();}
}
