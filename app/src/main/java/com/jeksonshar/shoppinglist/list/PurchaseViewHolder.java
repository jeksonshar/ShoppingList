package com.jeksonshar.shoppinglist.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeksonshar.shoppinglist.R;
import com.jeksonshar.shoppinglist.model.Purchase;

import java.io.File;

public class PurchaseViewHolder extends RecyclerView.ViewHolder {

    private final ImageView picturePurchaseView;
    private final TextView titlePurchaseView;
    private final TextView detailsPurchaseView;
    private final CheckBox completedView;

    private Purchase currentPurchase;
    private final PurchaseListAdapter.ItemEventsListener mListener;

    public PurchaseViewHolder(@NonNull View itemView, PurchaseListAdapter.ItemEventsListener listener) {
        super(itemView);

        this.mListener = listener;

        picturePurchaseView = itemView.findViewById(R.id.item_picture_view);
        titlePurchaseView = itemView.findViewById(R.id.item_title);
        detailsPurchaseView = itemView.findViewById(R.id.item_detail);
        completedView = itemView.findViewById(R.id.item_completed_purchase);

        completedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPurchase.setComplete(completedView.isChecked());
                mListener.onCompletedClick(currentPurchase);
            }
        });

        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(currentPurchase);
            }
        };
        itemView.setOnClickListener(itemClickListener);

        View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onLongItemClick(currentPurchase);
                return false;
            }
        };
        itemView.setOnLongClickListener(itemLongClickListener);
    }

    public void bindTo(final Purchase purchase) {
        this.currentPurchase = purchase;

        if (currentPurchase.getPicturePurchase() != null) {
            File purchasePicture = new File(currentPurchase.getPicturePurchase());
            if (purchasePicture.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(purchasePicture.getAbsolutePath());
                picturePurchaseView.setImageBitmap(bitmap);
            }
        }

        titlePurchaseView.setText(purchase.getTitle());
        detailsPurchaseView.setText(purchase.getDetail());
        completedView.setChecked(purchase.getComplete());
    }
}
