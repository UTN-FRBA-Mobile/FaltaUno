package com.faltauno.faltauno;

/**
 * Created by Chechu on 25/5/2017.
 */

public class Canchas {
    public String title;
    public String description;
    public int imageId;

    Canchas(String title, String description, int imageId) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
