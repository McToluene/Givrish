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
import android.widget.ImageView;

import com.example.givrish.R;
import com.example.givrish.interfaces.ItemSelectedListener;
import com.example.givrish.interfaces.OnCategoryFetchListener;
import com.example.givrish.models.AllItemsResponseData;
import com.example.givrish.models.AllItemsResponseImageData;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemSubCategoryData;
import com.example.givrish.viewmodel.ItemDetailsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ItemDetailsFragment extends Fragment implements View.OnClickListener, OnCategoryFetchListener{

  private static final String ITEM_KEY = "item";
  public static final String ITEM_DETAILS_TAG = "7";
  private ItemDetailsViewModel mViewModel;
  private ItemSelectedListener listener;
  private OnCategoryFetchListener fetchListener;
  private Executor executor = Executors.newSingleThreadExecutor();
  private ImageView img1,img2,img3,img4,img5;
  private MaterialTextView tvCate;
  private MaterialTextView tvSubCate;
  private String subCategoryId;
  private String categoryId;
  private CarouselView carouselView;

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
    mViewModel = ViewModelProviders.of(this).get(ItemDetailsViewModel.class);
    fetchListener = this;

    View view = inflater.inflate(R.layout.item_details_fragment, container, false);
    Toolbar detailsToolbar = view.findViewById(R.id.details_toolbar);

    detailsToolbar.setNavigationIcon(R.drawable.ic_close);
    detailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onCloseFragment(ITEM_DETAILS_TAG);
      }
    });

    carouselView = view.findViewById(R.id.carouselView);
    MaterialTextView tvItemName = view.findViewById(R.id.tv_itemName);
    MaterialTextView tvItemDescription = view.findViewById(R.id.tv_itemDesc);
    MaterialTextView tvLocation = view.findViewById(R.id.tv_itemLocation);
//    ImageView itemImage = view.findViewById(R.id.items_image);
    tvCate = view.findViewById(R.id.tv_ItemCate);
    tvSubCate = view.findViewById(R.id.tv_itemSubCat);
    MaterialTextView tvDateAdded = view.findViewById(R.id.tv_dateAdded);

    MaterialButton phone = view.findViewById(R.id.callButton);
    MaterialButton message = view.findViewById(R.id.messageButton);

    if (getArguments() != null) {
      final AllItemsResponseData item = getArguments().getParcelable(ITEM_KEY);

      final double location = getArguments().getDouble("LOCATION");
      int intLocation = (int) location;
      String appendLocation = intLocation + "km";
      tvLocation.setText(appendLocation);

      if (item != null) {
        tvItemName.setText(item.getItem_title());
        tvItemDescription.setText(item.getItem_description());
        tvDateAdded.setText(extractDate(item.getItem_joined()));
        getCategory(item.getItem_category_id());
        getSubCategory(item.getItem_sub_category_id());

        inflateCarousel(item.getItem_images());


//        if (item.getItem_images().size() != 0) {
//          String uri = url + item.getItem_images().get(0).getItemLargeImageName();
//          Picasso.get().load(uri).fit().placeholder(R.drawable.download).into(itemImage);
//        }

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

  private void inflateCarousel(final List<AllItemsResponseImageData> item_images) {
    final String url = "http://givrishapi.divinepagetech.com/uploadzuqwhdassigc6762373yughsbcjshd/";
    carouselView.setPageCount(item_images.size());
    ImageListener imageListener = new ImageListener() {
      @Override
      public void setImageForPosition(int position, ImageView imageView) {
         final String uri = url + item_images.get(position).getItemLargeImageName();
         Picasso.get().load(uri).fit().placeholder(R.drawable.download).into(imageView);
      }
    };

    carouselView.setImageListener(imageListener);
  }


  @NotNull
  private String extractDate(String date) {
    int index = date.indexOf(" ");
    return date.substring(0, index);
  }

  private void getCategory(final String id) {
    categoryId = id;
    executor.execute(new Runnable() {
      @Override
      public void run() {
        ItemCategoryData itemCategoryData = mViewModel.getCategory(id);
        if (itemCategoryData != null)
          fetchListener.onCategoryFetch(itemCategoryData.getItem_category_name());
        else
          fetchListener.onCategoryFetch("Others");
   }


    });
  }

  private void getSubCategory(final String id) {
    subCategoryId = id;
    executor.execute(new Runnable() {
      @Override
      public void run() {
        ItemSubCategoryData itemSubCategoryData = mViewModel.getSubCategory(id);
        if (itemSubCategoryData != null)
          fetchListener.onSubCategory(itemSubCategoryData.getItem_sub_category_name());
        else
          fetchListener.onSubCategory("Others");
      }
    });

  }

  @Override
  public void onClick(View v) {

  }

  @Override
  public void onCategoryFetch(String name) {
    tvCate.setText(name);
  }

  @Override
  public void onSubCategory(String name) {
    tvSubCate.setText(name);
    getChildFragmentManager().beginTransaction().replace(R.id.frame_similar , SimilarFragment.newInstance(categoryId,subCategoryId), "8").commit();
  }
}
