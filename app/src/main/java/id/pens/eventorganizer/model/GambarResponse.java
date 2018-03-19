package id.pens.eventorganizer.model;

/**
 * Created by MONKEY on 01/01/2018.
 */
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class GambarResponse {
    @SerializedName("data")
    public List<Gambar> gambars;

    public List<Gambar> getGambars() {
        return gambars;
    }

    public void setGambars(List<Gambar> gambars) {
        this.gambars = gambars;
    }

}
