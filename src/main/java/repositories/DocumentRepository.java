package repositories;

/**
 * Created by Tomek on 2015-11-15.
 */

import entities.AbstractEntity;
import entities.CordNode;
import response.ResponseList;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.Calendar;
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

    public List<AbstractEntity> getAll(String name) throws IOException {
        Query query = em.createQuery("FROM "+name, AbstractEntity.class);
        List<AbstractEntity> entities = query.getResultList();
        return entities;
    }
    public CordNode getCordNode (Integer id){

        Query query = em.createQuery("FROM CordNode d where d.cid=:id", CordNode.class);
        query.setParameter("id",id);
        long startTime = System.currentTimeMillis();
        CordNode  entity=(CordNode) query.getSingleResult();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        return entity;

    }

    public AbstractEntity get(String entityName, String field,String value) {
        Query query = em.createQuery("FROM " + entityName + " d where d."+field+"="+value, AbstractEntity.class);

        AbstractEntity  entity= (AbstractEntity) query.getSingleResult();

        return entity;
    }

}
