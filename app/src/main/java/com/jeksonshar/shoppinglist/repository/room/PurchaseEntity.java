package com.jeksonshar.shoppinglist.repository.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PurchaseEntity {

    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public String detail;
    public String picturePurchase;
    public boolean complete;
}
