package com.prepod.lightproduct;

public class Product {

    private Integer id;
    private String img;
    private String text;
    private String title;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
