package com.prepod.lightproduct.containers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prepod.lightproduct.containers.CreatedBy;

public class Review {

    int review_id;
    int rate;
    String text;
    String message;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }
}
