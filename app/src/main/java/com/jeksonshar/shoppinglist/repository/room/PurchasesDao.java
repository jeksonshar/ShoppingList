package com.jeksonshar.shoppinglist.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PurchasesDao {

    @Query("SELECT*FROM PurchaseEntity")
    List<PurchaseEntity> getAllPurchase();

    @Query("SELECT*FROM PurchaseEntity WHERE id == :id")
    PurchaseEntity getById(String id);

    @Insert
    void addPurchase(PurchaseEntity entity);

    @Delete
    void deletePurchase(PurchaseEntity entity);

    @Update
    void updatePurchase(PurchaseEntity entity);
}
