package com.jeksonshar.shoppinglist.repository.room;

import com.jeksonshar.shoppinglist.model.Purchase;

import java.util.UUID;

public class Converter {

    static PurchaseEntity convert(Purchase purchase) {

        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.id = purchase.getId().toString();
        purchaseEntity.title = purchase.getTitle();
        purchaseEntity.detail = purchase.getDetail();
                    purchaseEntity.picturePurchase = purchase.getPicturePurchase();
        purchaseEntity.complete = purchase.getComplete();

        return purchaseEntity;
    }

    static Purchase convert(PurchaseEntity purchaseEntity) {

        Purchase purchase = null;

        if (purchaseEntity != null) {

            purchase = new Purchase();

            purchase.setId(UUID.fromString(purchaseEntity.id));
            purchase.setTitle(purchaseEntity.title);
            purchase.setDetail(purchaseEntity.detail);
                        purchase.setPicturePurchase(purchaseEntity.picturePurchase);
            purchase.setComplete(purchaseEntity.complete);
        }
        return purchase;
    }
}
