package com.jeksonshar.shoppinglist.model;

import java.util.UUID;

public class Purchase {

    private UUID id;
    private String title;
    private String detail;

    private String picturePurchase;

    private boolean completed;

    public Purchase() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

        public String getPicturePurchase(){
               return picturePurchase;
        }

        public void setPicturePurchase(String picturePurchase) {
               this.picturePurchase = picturePurchase;
        }

    public boolean getComplete() {
        return completed;
    }

    public void setComplete(boolean complete) {
        this.completed = complete;
    }
}
