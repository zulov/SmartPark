package response;

/**
 * Created by Tomek on 2016-02-03.
 */
public class BasicResponse {
    String message;
    Status status;
    Long time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public BasicResponse(String message, Status status, Long time) {
        this.message = message;
        this.status = status;
        this.time = time;
    }
}
