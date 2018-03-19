package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 01/02/2018.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TiketResponse {

    @SerializedName("data")
    @Expose
    private List<Tiket> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Tiket> getData() {
        return data;
    }

    public void setData(List<Tiket> data) {
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