package com.example.givrish.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.givrish.Dashboard;
import com.example.givrish.R;
import com.example.givrish.models.ItemModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuProfileDialog extends DialogFragment implements View.OnClickListener{

    private Button btnEdit, btnDelete, btnViewed;
    private ImageView btnRemove;
    ItemModel item;

    public MenuProfileDialog() {
        // Required empty public constructor
    }

    public static DialogFragment newInstance(ItemModel itemModel) {
        MenuProfileDialog frag = new MenuProfileDialog();
        Bundle args = new Bundle();
        args.putParcelable("item", itemModel);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_menu_profile_dialog, null);

        if(getArguments()!=null){
            item=getArguments().getParcelable("item");
        }

        btnEdit=view.findViewById(R.id.btnEditItemProfile);
        btnDelete=view.findViewById(R.id.btnDeleteItemProfile);
        btnViewed=view.findViewById(R.id.btnViewedItemProfile);
        btnRemove=view.findViewById(R.id.btnCloseMenuDialog);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnViewed.setOnClickListener(this);
        btnRemove.setOnClickListener(this);

        setCancelable(false);
        builder.setView(view);
        Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditItemProfile:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
               AddItemFragment addItemFragment=AddItemFragment.newParcelableInstance(item);
               loadDetail(addItemFragment, Dashboard.ADD_ITEM_FRAGMENT_FLAG);
                break;
            case R.id.btnViewedItemProfile:
                Toast.makeText(getContext(), "Go to view details", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnDeleteItemProfile:
                Toast.makeText(getContext(), "Go to delete confirmation", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCloseMenuDialog:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

    private void loadDetail(Fragment fragment, String tag) {
        FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_layout, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }


}
