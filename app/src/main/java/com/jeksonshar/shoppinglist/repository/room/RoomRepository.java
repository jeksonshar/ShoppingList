package com.jeksonshar.shoppinglist.repository.room;

import android.content.Context;

import androidx.room.Room;

import com.jeksonshar.shoppinglist.model.Purchase;
import com.jeksonshar.shoppinglist.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomRepository extends BaseRepository {

    private PurchasesDao mPurchasesDao;

    public RoomRepository(Context context) {
        mPurchasesDao = Room
                .databaseBuilder(context, PurchasesDataBase.class, "purchase-database4.sqLite")
                .allowMainThreadQueries()
                .build()
                .getPurchasesDao();
    }

    @Override
    public List<Purchase> getAllPurchase() {
        List<PurchaseEntity> purchaseEntities = mPurchasesDao.getAllPurchase();
        List<Purchase> resultList = new ArrayList<>();

        for (PurchaseEntity purchaseEntity: purchaseEntities) {
            resultList.add(Converter.convert(purchaseEntity));
        }
        return resultList;
    }

    @Override
    public List<Purchase> getCompletedPurchases() {
        List<PurchaseEntity> purchaseEntities = mPurchasesDao.getAllPurchase();
        List<Purchase> resultList = new ArrayList<>();

        for (PurchaseEntity purchaseEntity: purchaseEntities) {
            if (Converter.convert(purchaseEntity).getComplete()) {
                resultList.add(Converter.convert(purchaseEntity));
            }
        }
        return resultList;
    }

    @Override
    public List<Purchase> getUncompletedPurchases() {
        List<PurchaseEntity> purchaseEntities = mPurchasesDao.getAllPurchase();
        List<Purchase> resultList = new ArrayList<>();

        for (PurchaseEntity purchaseEntity: purchaseEntities) {
            if (!Converter.convert(purchaseEntity).getComplete()) {
                resultList.add(Converter.convert(purchaseEntity));
            }
        }
        return resultList;
    }

    @Override
    public Purchase getPurchaseById(UUID uuid) {
        PurchaseEntity purchaseEntity = mPurchasesDao.getById(uuid.toString());

        return Converter.convert(purchaseEntity);
    }

    @Override
    public UUID addNewPurchase() {
        Purchase purchase = new Purchase();
        mPurchasesDao.addPurchase(Converter.convert(purchase));
        return purchase.getId();
    }

    @Override
    public void delete(Purchase purchase) {
        mPurchasesDao.deletePurchase(Converter.convert(purchase));
        notifyListeners();
    }

    @Override
    public void update(Purchase purchase) {
        mPurchasesDao.updatePurchase(Converter.convert(purchase));
        notifyListeners();
    }
}
