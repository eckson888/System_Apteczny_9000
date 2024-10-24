package org.gopnik.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.gopnik.model.Drug;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GeneralDrugDatabase implements IGeneralDrugDatabase{

    @PersistenceContext
    private EntityManager entityManager;
    private final String GET_BY_NAME= "SELECT i FROM com.gopnik.model.Drug i WHERE i.name =':name'";
    private final String GET_ALL="FROM com.gopnik.Drug";
    public GeneralDrugDatabase(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public Optional<Drug> getByName(String name) {
        TypedQuery<Drug> query = entityManager.createQuery(GET_BY_NAME, Drug.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    @Override
    public List<Drug> getAll() {
        TypedQuery<Drug> query = entityManager.createQuery(GET_ALL, Drug.class);
        List<Drug> result = query.getResultList();
        return result;
    }


}
