package com.findar_tech.findarv3;

public class Music {
    String name, duration, description;
    int songID, imageID;

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public int getImageID() { return imageID; }

    public void setImageID(int imageid) {imageID = imageid;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSong(int ID) {
        songID = ID;
    }

    public int getSongID() {
        return songID;
    }
}
