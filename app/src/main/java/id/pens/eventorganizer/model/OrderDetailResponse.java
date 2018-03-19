package id.pens.eventorganizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MONKEY on 01/02/2018.
 */

public class OrderDetailResponse {
    @SerializedName("data")
    @Expose
    private OrderDetail data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
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
