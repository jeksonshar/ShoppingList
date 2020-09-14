package com.jeksonshar.shoppinglist.details;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.jeksonshar.shoppinglist.R;
import com.jeksonshar.shoppinglist.model.Purchase;
import com.jeksonshar.shoppinglist.repository.Repository;
import com.jeksonshar.shoppinglist.repository.RepositoryProvider;

import java.io.File;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class PurchaseDetailsFragment extends Fragment {

    private static final String KEY_ID = "ID";
    private static final int GALLERY_REQUEST = 1;

    private String picturePurchase = "";

    // model
    private UUID purchaseId;
    private Purchase mPurchase;

    private Repository mRepository;

    // view
    private EditText purchaseTitleView;
    private EditText purchaseDetailView;
    private CheckBox purchaseIsComplete;
    private ImageButton addPicture;
    private ImageButton deletePicture;
    private ImageView imageViewPicture;

    public PurchaseDetailsFragment() {
        super(R.layout.fragment_purchases_details);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Bundle arguments = getArguments();

        purchaseId = (UUID) arguments.getSerializable(KEY_ID);
        mRepository = RepositoryProvider.getInstance(getContext());
        mPurchase = mRepository.getPurchaseById(purchaseId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        purchaseTitleView = view.findViewById(R.id.purchase_title_view);
        purchaseDetailView = view.findViewById(R.id.purchase_details_view);
        purchaseIsComplete = view.findViewById(R.id.complete_check_box_of_purchase);
        addPicture = view.findViewById(R.id.add_picture);
        deletePicture = view.findViewById(R.id.delete_picture);
        imageViewPicture = view.findViewById(R.id.image_view_picture);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        purchaseTitleView.setText(mPurchase.getTitle());
        purchaseDetailView.setText(mPurchase.getDetail());

        purchaseIsComplete.setChecked(mPurchase.getComplete());

        picturePurchase = mPurchase.getPicturePurchase();
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && picturePurchase != null) {
                final File externalStorage = Environment.getDataDirectory();
                if (externalStorage != null) {
                    imageViewPicture.setImageBitmap(BitmapFactory.decodeFile(picturePurchase));
                }
            }

        savePurchase();

        purchaseTitleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPurchase.setTitle(s.toString());
                savePurchase();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        purchaseDetailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPurchase.setDetail(s.toString());
                savePurchase();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        purchaseIsComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPurchase.setComplete(isChecked);
                savePurchase();
            }
        });

        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPictureFromGallery();
            }
        });

        deletePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePictureView();
            }
        });

        imageViewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + picturePurchase), "image/*");
                startActivity(intent);
            }
        });
    }

    private void savePurchase() {
        mRepository.update(mPurchase);
    }

    private void addPictureFromGallery() {
        Intent pictureFromGalleryIntent = new Intent(Intent.ACTION_PICK);
        pictureFromGalleryIntent.setType("image/*");
        startActivityForResult(pictureFromGalleryIntent, GALLERY_REQUEST);
    }

    private void deletePictureView() {
        picturePurchase = null;
        imageViewPicture.setImageResource(R.drawable.ic_baseline_image_192);
        mPurchase.setPicturePurchase(picturePurchase);
        savePurchase();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri mUri = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(mUri,
                    filePathColumn,
                    null,
                    null,
                    null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePurchase = cursor.getString(columnIndex);
            cursor.close();

            imageViewPicture.setImageBitmap(BitmapFactory.decodeFile(picturePurchase));
            mPurchase.setPicturePurchase(picturePurchase);

            savePurchase();
        }
    }

    public static PurchaseDetailsFragment makeInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ID, id);

        PurchaseDetailsFragment fragment = new PurchaseDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
