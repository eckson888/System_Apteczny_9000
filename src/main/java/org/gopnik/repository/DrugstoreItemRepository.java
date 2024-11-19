package org.gopnik.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.gopnik.model.Drugstore;
import org.gopnik.model.DrugstoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DrugstoreItemRepository implements DrugstoreItemInterface {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    JpaDrugstoreItemInterface jpaDrugstoreItemInterface;


    private final String GET_BY_ID = "SELECT i FROM DrugstoreItem i WHERE i.id = :id";

    private final String GET_DRUGSTORE_ITEM_BY_DRUG_ID = "SELECT i FROM DrugstoreItem i WHERE i.drug.id = :id AND i.drugstore.id != :drugstoreId";

    private final String GET_BY_DRUGSTORE_ID = "SELECT i FROM DrugstoreItem i WHERE i.drugstore.id = :id";

    private final String GET_BY_NAME_AND_COMMONNAME = "SELECT i FROM DrugstoreItem i WHERE LOWER (i.drug.name) " +
            " LIKE LOWER(:keyword) OR LOWER(i.drug.commonName) LIKE LOWER(:keyword)";

    private final String GET_ALL = "SELECT i FROM DrugstoreItem i ORDER BY i.id";

    private final String GET_ALL_EXCLUDE_CURRENT_ID = "SELECT i FROM DrugstoreItem i " +
            "WHERE i.id IN (" +
            "  SELECT MIN(innerItem.id) FROM DrugstoreItem innerItem " +
            "  WHERE innerItem.drugstore.id != :id " +
            "  GROUP BY innerItem.drug.id" +
            ") " +
            "ORDER BY i.drug.name";

    private final String GET_BY_KEYWORD_AND_DRUGSTOREID = "SELECT i from DrugstoreItem i WHERE (LOWER (i.drug.name)" +
            " LIKE LOWER(:keyword) OR LOWER(i.drug.commonName) LIKE LOWER(:keyword)) AND i.drugstore.id = :id";

    private final String GET_BY_KEYWORD_EXCLUDE_CURRENT_ID = "SELECT i FROM DrugstoreItem i " +
            "WHERE i.id IN (" +
            "  SELECT MIN(innerItem.id) FROM DrugstoreItem innerItem " +
            "  WHERE (LOWER(innerItem.drug.name) LIKE LOWER(:keyword) OR LOWER(innerItem.drug.commonName) LIKE LOWER(:keyword)) " +
            "  AND innerItem.drugstore.id != :id " +
            "  GROUP BY innerItem.drug.id" +
            ")";

    private final String GET_BY_ID_AND_DRUGSTOREID = "SELECT i from DrugstoreItem i WHERE " +
            "i.drug.id = :drugid AND i.drugstore.id = :drugstoreid";

    @Override
    public List<DrugstoreItem> getDrugstoreInventory(Long drugstoreId) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_BY_DRUGSTORE_ID, DrugstoreItem.class);
        query.setParameter("id", drugstoreId);
        query.setMaxResults(1);
        DrugstoreItem result = query.getSingleResult();
        if (result == null) {
            return null; //TODO lepszy return zrobic
        } else {
            return result.getDrugstore().getInventory();
        }
    }

    @Override
    public List<Drugstore> getDrugstoresByDrugstoreItemId(Long drugstoreItemId,Long currentDrugstoreId)
    {
        DrugstoreItem temp = getById(drugstoreItemId).orElse(null);
        Long forbiddenDrugstoreId = currentDrugstoreId;
        Long drugId = (long) temp.getDrug().getId();
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_DRUGSTORE_ITEM_BY_DRUG_ID, DrugstoreItem.class);
        query.setParameter("id", drugId);
        query.setParameter("drugstoreId",forbiddenDrugstoreId);
        List<DrugstoreItem> query_results;
        try {
             query_results = Optional.of(query.getResultList()).orElse(null);
        } catch (NoResultException e) {
             query_results = null;
        }

        List<Drugstore> result = new ArrayList<>();
        for(DrugstoreItem d: query_results)
        {
                result.add(d.getDrugstore());

        }
        return result;
    }


    @Override
    public Optional<DrugstoreItem> getById(Long id) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_BY_ID, DrugstoreItem.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DrugstoreItem> findByKeywordInSomeDrugstore(String keyword, Long id) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_BY_KEYWORD_AND_DRUGSTOREID, DrugstoreItem.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("id", id);

        List<DrugstoreItem> result = query.getResultList();
        return result;
    }
    @Override
    public List<DrugstoreItem> findItemsExcludeCurrentDrugstoreId(String keyword, Long id) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_BY_KEYWORD_EXCLUDE_CURRENT_ID, DrugstoreItem.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("id", id);

        List<DrugstoreItem> result = query.getResultList();
        return result;
    }

    @Override
    public List<DrugstoreItem> findByKeywordInAllDrugstores(String keyword) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_BY_NAME_AND_COMMONNAME, DrugstoreItem.class);
        query.setParameter("keyword", "%" + keyword + "%");

        List<DrugstoreItem> result = query.getResultList();
        return result;
    }

    @Override
    public List<DrugstoreItem> findByIdInGivenDrugstore(int drugid, Long drugstoreid) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_BY_ID_AND_DRUGSTOREID, DrugstoreItem.class);
        query.setParameter("drugid", drugid);
        query.setParameter("drugstoreid", drugstoreid);

        return query.getResultList();
    }

    @Override
    public List<DrugstoreItem> getAll() {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_ALL, DrugstoreItem.class);
        List<DrugstoreItem> result = query.getResultList();
        return result;
    }
    @Override
    public List<DrugstoreItem> getAllExcludingCurrentDrugstoreId(Long drugstoreId) {
        TypedQuery<DrugstoreItem> query = entityManager.createQuery(GET_ALL_EXCLUDE_CURRENT_ID, DrugstoreItem.class);
        query.setParameter("id",drugstoreId);
        List<DrugstoreItem> result = query.getResultList();

        return result;
    }

    public void save(DrugstoreItem item) {
        this.jpaDrugstoreItemInterface.save(item);
    }


}
