package id.pens.eventorganizer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MONKEY on 01/01/2018.
 */

public class Gambar {
    @SerializedName("title")
    public String title;
    @SerializedName("imageUrl")
    public String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
