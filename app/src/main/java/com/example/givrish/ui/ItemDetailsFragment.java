package com.example.givrish.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.givrish.R;
import com.example.givrish.interfaces.ItemSelectedListener;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.viewmodel.ItemDetailsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;



public class ItemDetailsFragment extends Fragment implements View.OnClickListener{

  private static final String ITEM_KEY = "item";
  public static final String ITEM_DETAILS_TAG = "7";
  private ItemDetailsViewModel mViewModel;
  private MaterialTextView tvDateAdded;
  private ItemSelectedListener listener;
  private ImageView img1,img2,img3,img4,img5;

  public static ItemDetailsFragment newInstance() {
    return new ItemDetailsFragment();
  }

  public static ItemDetailsFragment newInstance(AllItemsResponseData item, double location) {

    Bundle args = new Bundle();
    args.putParcelable(ITEM_KEY, item);
    args.putDouble("LOCATION", location);
    ItemDetailsFragment fragment = new ItemDetailsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    listener = (ItemSelectedListener) context;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.item_details_fragment, container, false);
    Toolbar detailsToolbar = view.findViewById(R.id.details_toolbar);

    detailsToolbar.setNavigationIcon(R.drawable.ic_close);
    detailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onCloseItem(ITEM_DETAILS_TAG);
      }
    });


    String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";
    MaterialTextView tvItemName = view.findViewById(R.id.tv_itemName);
    MaterialTextView tvLocation = view.findViewById(R.id.tv_itemLocation);
    ImageView itemImage = view.findViewById(R.id.items_image);
    MaterialTextView tvCate = view.findViewById(R.id.tv_ItemCate);
    MaterialTextView tvSubCate = view.findViewById(R.id.tv_itemSubCat);
    tvDateAdded = view.findViewById(R.id.tv_dateAdded);

    MaterialButton phone = view.findViewById(R.id.callButton);
    MaterialButton message = view.findViewById(R.id.messageButton);

    if (getArguments() != null) {
      final AllItemsResponseData item = getArguments().getParcelable(ITEM_KEY);
      final double location = getArguments().getDouble("LOCATION");

      tvLocation.setText(String.valueOf(location));

      if (item != null) {
        tvItemName.setText(item.getItem_title());
        tvSubCate.setText(item.getItem_sub_category_id());
        tvCate.setText(item.getItem_category_id());
        if (item.getItem_images().size() != 0) {
          String uri = url + item.getItem_images().get(0).getItemLargeImageName();
          Picasso.with(getContext()).load(uri).fit().placeholder(R.drawable.download).into(itemImage);
        }

        phone.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.getPhone_number(), null)));
          }
        });

        message.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
            intent.putExtra("address", item.getPhone_number());
            if (getActivity() != null) {
              if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                startActivity(intent);
            }

          }
        });
      }
    }
    return view;
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
    // TODO: Use the ViewModel

  }

  @Override
  public void onClick(View v) {

  }
}
