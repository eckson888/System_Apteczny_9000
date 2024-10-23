package org.gopnik.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GeneralDrugDatabase implements IGeneralDrugDatabase{

    @PersistenceContext
    private EntityManager entityManager;
    public GeneralDrugDatabase(EntityManager entityManager){
        this.entityManager = entityManager;
    }


}
