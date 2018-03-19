package id.pens.eventorganizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MONKEY on 02/01/2018.
 */

public class DetailEvent {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DetailEventData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailEventData getData() {
        return data;
    }

    public void setData(DetailEventData data) {
        this.data = data;
    }

}
