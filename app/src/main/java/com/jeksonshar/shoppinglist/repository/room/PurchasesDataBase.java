package com.jeksonshar.shoppinglist.repository.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {PurchaseEntity.class},
        version = 1,
        exportSchema = false
)

public abstract class PurchasesDataBase extends RoomDatabase {
    public abstract PurchasesDao getPurchasesDao();
}
