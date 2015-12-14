package response;

import entities.Document;

import java.util.List;

/**
 * Created by Tomek on 2015-11-22.
 */
public class ResponseList {
    int hits;
    Long time;
    List<Document> documents;


    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
