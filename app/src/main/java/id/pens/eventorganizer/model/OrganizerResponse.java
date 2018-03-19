package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 02/02/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganizerResponse {

    @SerializedName("data")
    @Expose
    private List<Organizer> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Organizer> getData() {
        return data;
    }

    public void setData(List<Organizer> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}