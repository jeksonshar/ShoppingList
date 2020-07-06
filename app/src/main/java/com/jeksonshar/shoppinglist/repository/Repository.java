package com.jeksonshar.shoppinglist.repository;

import com.jeksonshar.shoppinglist.model.Purchase;

import java.util.List;
import java.util.UUID;

public interface Repository {

    List<Purchase> getAllPurchase();

    List<Purchase> getCompletedPurchases();

    List<Purchase> getUncompletedPurchases();

    Purchase getPurchaseById(UUID uuid);

    UUID addNewPurchase();

    void delete(Purchase purchase);

    void update(Purchase purchase);

    void addListener(Listener listener);

    void removeListener(Listener listener);

    interface Listener {
        void onDataChanged();
    }
}
