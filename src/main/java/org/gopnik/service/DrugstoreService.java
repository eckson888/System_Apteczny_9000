package org.gopnik.service;

import jakarta.transaction.Transactional;
import org.gopnik.model.Drugstore;
import org.gopnik.repository.EmployeeRepository;
import org.gopnik.repository.IDrugstoreDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugstoreService implements IDrugstoreService {

    @Autowired
    private IDrugstoreDatabase drugstoreRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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

    public Optional<Drugstore> findById(Long id){ return drugstoreRepository.findById(id);}

    public Optional<Drugstore> getCurrentDrugstore() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return Optional.ofNullable(drugstoreRepository.findById(employeeRepository.findByUsername(username).orElse(null).getDrugstoreId()).orElse(null));

    }

}
