package org.gopnik.service;

import jakarta.transaction.Transactional;
import org.gopnik.model.Drug;
import org.gopnik.model.DrugstoreItem;
import org.gopnik.repository.DrugstoreItemRepository;
import org.gopnik.repository.GeneralDrugDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrugstoreItemService {
    @Autowired
    private DrugstoreItemRepository drugs;
    @Autowired
    private GeneralDrugDatabase generalDrugDatabase;

    public List<DrugstoreItem> fetchDrugstoreItemList() {
        return (List<DrugstoreItem>) drugs.findAll();
    }
//    @Autowired
//    public Optional<Drug> matchItemToDatabase(Long id){   chuj wie czemu to nie dziala
//        return generalDrugDatabase.getById(id);           trzeba było wyjebać żeby zatrybiło
//    }

    public List<Drug> fetchDrugsFromDatabase(){
        List<DrugstoreItem> d = drugs.findAll();
        List<Drug> returnedList = new ArrayList<>();
        for (DrugstoreItem item : d){
            //System.out.println(item.getId()); debug Xd
            returnedList.add(generalDrugDatabase.getById((long) item.getDrug().getId()).orElse(null));
        }
        return returnedList;
    }
}
