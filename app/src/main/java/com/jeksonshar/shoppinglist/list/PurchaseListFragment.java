package com.jeksonshar.shoppinglist.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeksonshar.shoppinglist.R;
import com.jeksonshar.shoppinglist.details.PurchaseDetailsFragment;
import com.jeksonshar.shoppinglist.model.Purchase;
import com.jeksonshar.shoppinglist.repository.Repository;
import com.jeksonshar.shoppinglist.repository.RepositoryProvider;

import java.util.List;

public class PurchaseListFragment extends Fragment {

    enum ChooseListPurchase {
        ALL_PURCHASES,
        COMPLETED_PURCHASES,
        UNCOMPLETED_PURCHASES
    }

    private RecyclerView mRecyclerView;
    private PurchaseListAdapter mPurchaseListAdapter;
    private ImageButton addPurchase;

    private Repository mRepository;

    private static ChooseListPurchase listPurchase = ChooseListPurchase.ALL_PURCHASES;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mRepository = RepositoryProvider.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchases_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        addPurchase = view.findViewById(R.id.fab);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        makeListPurchase();

        addPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.fab) {
                    FragmentTransaction transaction = getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, PurchaseDetailsFragment
                                    .makeInstance(mRepository.addNewPurchase()));
                    transaction = transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
    }

    private void makeListPurchase() {
        List<Purchase> purchaseList;
        if (getListPurchase().equals(ChooseListPurchase.COMPLETED_PURCHASES)) {
            purchaseList = mRepository.getCompletedPurchases();
        } else if (getListPurchase().equals(ChooseListPurchase.UNCOMPLETED_PURCHASES)) {
            purchaseList = mRepository.getUncompletedPurchases();
        } else {
            purchaseList = mRepository.getAllPurchase();
        }

        mPurchaseListAdapter = new PurchaseListAdapter(purchaseList, mItemEventsListener);
        mRecyclerView.setAdapter(mPurchaseListAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.completed_purchases_menu) {
            setListPurchase(ChooseListPurchase.COMPLETED_PURCHASES);
            makeListPurchase();
        } else if (item.getItemId() == R.id.uncompleted_purchases_menu) {
            setListPurchase(ChooseListPurchase.UNCOMPLETED_PURCHASES);
            makeListPurchase();
        } else if (item.getItemId() == R.id.all_purchases_menu) {
            setListPurchase(ChooseListPurchase.ALL_PURCHASES);
            makeListPurchase();
        }
        return super.onOptionsItemSelected(item);
    }

    private ChooseListPurchase getListPurchase() {
        return listPurchase;
    }

    private void setListPurchase(ChooseListPurchase listPurchase) {
        PurchaseListFragment.listPurchase = listPurchase;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRepository.addListener(repositoryListener);
        makeListPurchase();
    }

    @Override
    public void onPause() {
        mRepository.removeListener(repositoryListener);
        super.onPause();
    }

    private final Repository.Listener repositoryListener = new Repository.Listener() {
        @Override
        public void onDataChanged() {
            List<Purchase> purchaseList;

            if (getListPurchase().equals(ChooseListPurchase.COMPLETED_PURCHASES)) {
                purchaseList = mRepository.getCompletedPurchases();
            } else if (getListPurchase().equals(ChooseListPurchase.UNCOMPLETED_PURCHASES)) {
                purchaseList = mRepository.getUncompletedPurchases();
            } else {
                purchaseList = mRepository.getAllPurchase();
            }

            mPurchaseListAdapter.setNewPurchases(purchaseList);
        }
    };

    private final PurchaseListAdapter.ItemEventsListener mItemEventsListener
            = new PurchaseListAdapter.ItemEventsListener() {
        @Override
        public void onCompletedClick(Purchase purchase) {
            mRepository.update(purchase);
        }

        @Override
        public void onItemClick(Purchase purchase) {
            FragmentTransaction transaction = getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,
                            PurchaseDetailsFragment.makeInstance(purchase.getId()));
            transaction.addToBackStack(null);
            transaction.commit();
        }

        @Override
        public void onLongItemClick(Purchase purchase) {
            ConfirmationDialogOfDeleteFragment.makeInstance(purchase)
                    .show(getParentFragmentManager(), null);
        }
    };
}
