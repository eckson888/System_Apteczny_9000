package org.gopnik.service;

import org.gopnik.model.Drug;
import org.gopnik.model.Drugstore;
import org.gopnik.repository.GeneralDrugDatabase;
import org.gopnik.repository.IDrugstoreDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugService {

    @Autowired
    GeneralDrugDatabase generalDrugDatabase;

    public List<Drug> getByKeyword(String keyword){
        return generalDrugDatabase.getByKeyword(keyword);
    }
    public Drug findById(int id){
        return generalDrugDatabase.getById(id).orElseThrow();
    }

}
