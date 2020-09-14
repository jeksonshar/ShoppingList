package com.jeksonshar.shoppinglist.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeksonshar.shoppinglist.R;
import com.jeksonshar.shoppinglist.model.Purchase;

import java.util.List;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseViewHolder> {

    private List<Purchase> mPurchaseList;
    private final ItemEventsListener mListener;
    private Activity mActivity;

    public PurchaseListAdapter(List<Purchase> purchaseList, ItemEventsListener listener, Activity activity) {
        mPurchaseList = purchaseList;
        mListener = listener;
        mActivity = activity;

        setHasStableIds(true);
    }

    void setNewPurchases(List<Purchase> newPurchases){
        this.mPurchaseList = newPurchases;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return mPurchaseList.get(position).getId().hashCode();
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_purchase, parent, false);
        return new PurchaseViewHolder(itemView, mListener, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        holder.bindTo(mPurchaseList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPurchaseList.size();
    }

    interface ItemEventsListener {
        void onCompletedClick(Purchase purchase);
        void onItemClick(Purchase purchase);
        void onLongItemClick(Purchase purchase);
    }
}
