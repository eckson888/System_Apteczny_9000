package org.gopnik.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.gopnik.model.Drug;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GeneralDrugDatabase implements IGeneralDrugDatabase{

    @PersistenceContext
    private EntityManager entityManager;

    private final String GET_BY_NAME= "SELECT i FROM Drug i WHERE i.name =':name'";

    private final String GET_100_BY_ALL=
            "SELECT i FROM Drug i " +
                    "WHERE LOWER(i.name) LIKE LOWER(:keyword) " +
                    "OR LOWER(i.activeSubstance) LIKE LOWER(:keyword) " +         //to query moznaby zmienic jakos zeby lepsze bylo
                    "OR LOWER(i.pharmaceuticalForm) LIKE LOWER(:keyword) " +      //TODO
                    "ORDER BY i.name";

    private final String GET_ALL="FROM com.gopnik.Drug";    //zostawiam to bo nie moge XDDDXDXDD medal dla uzytkownika zouek

    private final String GET_BY_ID="SELECT i FROM Drug i " +
            "WHERE i.id = :id";



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
    public Optional<Drug> getById(int id){
        TypedQuery<Drug> query = entityManager.createQuery(GET_BY_ID, Drug.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Drug> getByKeyword(String keyword){
        TypedQuery<Drug> query = entityManager.createQuery(GET_100_BY_ALL, Drug.class);
        query.setParameter("keyword", keyword + "%");
        return query.getResultList();
    }

    @Override
    public List<Drug> getAll() {
        TypedQuery<Drug> query = entityManager.createQuery(GET_ALL, Drug.class);
        List<Drug> result = query.getResultList();
        return result;
    }


}
