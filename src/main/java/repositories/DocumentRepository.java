package repositories;

/**
 * Created by Tomek on 2015-11-15.
 */

import entities.AbstractEntity;
import entities.CordNode;
import entities.Parking;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Stateful
@LocalBean
public class DocumentRepository {

    @PersistenceContext(unitName = "smartpark")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(AbstractEntity entity) {
        em.persist(entity);
    }

    public List<AbstractEntity> getAll(String name) {
        Query query = em.createQuery("FROM "+name, AbstractEntity.class);
        List<AbstractEntity> entities = query.getResultList();
        return entities;
    }
    public CordNode getCordNode (Long id){
        Query query = em.createQuery("FROM CordNode d where d.cid=:id", CordNode.class);
        query.setParameter("id",id);
        CordNode  entity=(CordNode) query.getSingleResult();
        return entity;
    }

    public AbstractEntity get(String entityName, String field,String value) {
        Query query = em.createQuery("FROM " + entityName + " d where d."+field+"="+value, AbstractEntity.class);
        Object o=query.getSingleResult();
        AbstractEntity  entity= (AbstractEntity) o;

        return entity;
    }

    public List<AbstractEntity> getList(String entityName, String field,String value) {
        Query query = em.createQuery("FROM " + entityName + " d where d."+field+"="+value, AbstractEntity.class);

        List<AbstractEntity> entities = query.getResultList();

        return entities;
    }

    public List<CordNode> findIds(List<Long> ids) {
        Query query = em.createQuery("FROM CordNode d where d.cid in (:ids)");
        query.setParameter("ids",ids);
        List<CordNode> documents=query.getResultList();

        return documents;
    }
    public Parking getParking(Long id) {
        Query query = em.createQuery("FROM Parking p where p.localization="+id, Parking.class);

        Parking entity= (Parking) query.getSingleResult();

        return entity;
    }
}
