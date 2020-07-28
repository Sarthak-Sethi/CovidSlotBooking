package com.example.covidslotbooking;
public class item {

    private String title;
    private String description;
    private boolean isShrink = true;

    public item(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}