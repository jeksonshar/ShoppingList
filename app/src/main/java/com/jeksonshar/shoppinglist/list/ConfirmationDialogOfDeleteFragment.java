package com.jeksonshar.shoppinglist.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jeksonshar.shoppinglist.R;
import com.jeksonshar.shoppinglist.model.Purchase;
import com.jeksonshar.shoppinglist.repository.Repository;
import com.jeksonshar.shoppinglist.repository.RepositoryProvider;

import java.util.UUID;

public class ConfirmationDialogOfDeleteFragment extends DialogFragment {

    private static final String KEY_ID = "ID";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.delete_purchase))
                .setMessage(getResources().getString(R.string.are_you_sure_to_delete_purchase))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCurrentPurchase();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create();
    }

    private void deleteCurrentPurchase() {
        Repository repository = RepositoryProvider.getInstance(getContext());
        UUID purchaseId = (UUID) getArguments().getSerializable(KEY_ID);
        Purchase purchaseToDelete = repository.getPurchaseById(purchaseId);
        repository.delete(purchaseToDelete);
    }

    public static DialogFragment makeInstance(Purchase purchase) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ID, purchase.getId());

        ConfirmationDialogOfDeleteFragment fragment = new ConfirmationDialogOfDeleteFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
