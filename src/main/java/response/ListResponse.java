package response;

/**
 * Created by Tomek on 2016-02-03.
 */
public class ListResponse extends BasicResponse {
    int hits;

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public ListResponse(String message, Status status, Long time, int hits) {
        super(message, status, time);
        this.hits = hits;
    }
}
