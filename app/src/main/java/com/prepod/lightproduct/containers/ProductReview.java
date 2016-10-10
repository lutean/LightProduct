package com.prepod.lightproduct.containers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prepod.lightproduct.containers.CreatedBy;

public class ProductReview {

    private Integer id;
    private Integer product;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy;
    private Integer rate;
    private String text;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProduct() {
        return product;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public Integer getRate() {
        return rate;
    }

    public String getText() {
        return text;
    }
}
