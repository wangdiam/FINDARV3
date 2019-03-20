package com.findar_tech.findarv3;

public class Music {
    private String name, duration, description;
    private int songID, imageID;

    public Music(String name, String duration, String description, int songID, int imageID) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.songID = songID;
        this.imageID = imageID;
    }

    public Music() {

    }

    public String getName() {
        return name;
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
