package repositories;

/**
 * Created by Tomek on 2015-11-15.
 */

import entities.Document;

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
    public void savePs(Document document) {
        em.persist(document);
    }



    public List<Document> listAllPostgres() {
        Query query = em.createNamedQuery("Post.all");
        return (List<Document>) query.getResultList();
    }

    public void addToPS(Document document) {
        em.persist(document);
    }


    public ResponseList findPs(String text,String field) throws IOException {
        long millisStart = Calendar.getInstance().getTimeInMillis();

        Query query = em.createNativeQuery("SELECT * FROM document d where d."+field+" like '"+text+"'",Document.class);
        List<Document> documents=query.getResultList();

        long millisEnd = Calendar.getInstance().getTimeInMillis();
        ResponseList rl= new ResponseList();
        rl.setHits(documents.size());
        rl.setDocuments(documents);
        rl.setTime(millisEnd-millisStart);
        return rl;
    }

    public ResponseList findPs(List<Integer> ids) {
        Query query = em.createQuery("FROM Document d where d.id in (:ids)");
        query.setParameter("ids",ids);
        List<Document> documents=query.getResultList();

        ResponseList rl= new ResponseList();
        rl.setHits(documents.size());
        rl.setDocuments(documents);

        return rl;
    }

}
