package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.givrish.Dashboard;
import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.models.AddItemResponse;
import com.example.givrish.models.AddItemResponseData;
import com.example.givrish.models.ItemModel;
import com.example.givrish.models.ItemSubCategoryData;
import com.example.givrish.R;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ItemCategoryModel;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.models.ItemColor;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import com.example.givrish.viewmodel.AddItemViewModel;

public class AddItemFragment extends Fragment {

  private AddItemViewModel mViewModel;
  private int RequestCode = 100;
  private Options options;
  private LinearLayout layout;
  private ArrayList<String> returnValue = new ArrayList<>();
  private List<ItemCategoryData> itemCategoryDataList;
  private AutoCompleteTextView mainCategory;
  private AutoCompleteTextView subCategory;
  private AutoCompleteTextView colorSpinner;
  private  static final String apiKey= "com.example.givrish.ui.APIKEY";
  private List<ItemSubCategoryData> itemSubCategoryDataList;
  private int selectedPosition;
  private ApiEndpointInterface apiService;
  private List<Bitmap> imageArray;
  private TextInputEditText itemName;
  private TextInputEditText itemDesc;
  private Gson gson;
  private List<String> imagePaths = new ArrayList<>();
  private FloatingActionButton addButton;
  private CallBackListener listener;

  public static AddItemFragment newInstance() {
    return new AddItemFragment();
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof CallBackListener)
      listener = (CallBackListener) context;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);

    View view = inflater.inflate(R.layout.add_item_fragment, container, false);
    Toolbar toolbar = view.findViewById(R.id.add_toolbar);
    mainCategory = view.findViewById(R.id.mainCategory);
    subCategory = view.findViewById(R.id.subCategory);
    colorSpinner =  view.findViewById(R.id.ItemColor);
    addButton = view.findViewById(R.id.addImagebtn);

    toolbar.setTitle("Add Item");

    if(getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
    }
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);

    // TODO: Use the ViewModel
    itemCategoryDataList = mViewModel.getLiveItemCategories().getValue();
    mViewModel.getLiveItemCategories().observe(this, new Observer<List<ItemCategoryData>>() {
      @Override
      public void onChanged(List<ItemCategoryData> itemCategoryData) {
        itemCategoryDataList = itemCategoryData;
        ArrayAdapter<ItemCategoryData> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemCategoryData);
        mainCategory.setAdapter(arrayAdapter);
      }
    });


    apiCategoryList(apiKey);
    apiSubCategory(apiKey);

    itemSubCategoryDataList = mViewModel.getLiveSubCategories().getValue();
    mViewModel.getLiveSubCategories().observe(this, new Observer<List<ItemSubCategoryData>>() {
      @Override
      public void onChanged(List<ItemSubCategoryData> itemSubCategoryData) {
        itemSubCategoryDataList = itemSubCategoryData;
      }
    });


    ArrayAdapter<ItemColor> colorArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, populateColor());
    colorArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    colorSpinner.setAdapter(colorArrayAdapter);


    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        options = Options.init()
                .setRequestCode(100)
                .setCount(3)
                .setFrontfacing(false)
                .setImageQuality(ImageQuality.REGULAR)
                .setPreSelectedUrls(returnValue)
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                .setPath("/pix/images");

        Pix.start(getActivity(), options);
      }
    });

    mainCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
          ItemCategoryData selectedItem =  itemCategoryDataList.get(position);
          getSub(selectedItem);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.add_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home)
      listener.onBackClick(Dashboard.ADD_ITEM_FRAGMENT_FLAG);
    return super.onOptionsItemSelected(item);

  }

  private List populateColor() {
    List<ItemColor> colors = new ArrayList<>();
    colors.add(new ItemColor("0", "none"));
    colors.add(new ItemColor("1", "red"));
    colors.add(new ItemColor("2", "black"));
    colors.add(new ItemColor("3", "white"));
    colors.add(new ItemColor("4", "blue"));
    colors.add(new ItemColor("5", "pink"));
    colors.add(new ItemColor("6", "brown"));
    colors.add(new ItemColor("7", "orange"));
    colors.add(new ItemColor("8", "purple"));
    colors.add(new ItemColor("9", "green"));
    colors.add(new ItemColor("10", "yellow"));
    colors.add(new ItemColor("11", "silver"));
    colors.add(new ItemColor("12", "grey"));
    return colors;
  }

  private void apiSubCategory(final String apiKey) {
    ItemCategoryModel itemCategoryModel = new ItemCategoryModel(apiKey);
    String itemString = gson.toJson(itemCategoryModel);
    Call<ItemSubCategoryResponse> call = apiService.getSubCategory(itemString);
    call.enqueue(new Callback<ItemSubCategoryResponse>() {
      @Override
      public void onResponse(@NonNull Call<ItemSubCategoryResponse> call,@NonNull Response<ItemSubCategoryResponse> response) {
        if (response.body() != null && response.body().getResponseCode().equals("1")){
          mViewModel.insertAllSub(response.body().getData());
        }
      }

      @Override
      public void onFailure(Call<ItemSubCategoryResponse> call, Throwable t) {
        Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void getSub(ItemCategoryData selected) {
    Log.i("SELECT", selected.getItem_category_id());
    final List<ItemSubCategoryData> subCategoryOfCategory = new ArrayList<>();
    for (int i = 0; i < itemSubCategoryDataList.size(); i++) {
      if (itemSubCategoryDataList.get(i).getItem_category_id().equals(selected.getItem_category_id())){
        subCategoryOfCategory.add(itemSubCategoryDataList.get(i));
      }
    }

    ArrayAdapter<ItemSubCategoryData> arrayAdapter;
    if (getContext()!= null) {
     arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, subCategoryOfCategory);
      subCategory.setAdapter(arrayAdapter);
    }

  }


  private void apiCategoryList(String key) {
    ItemCategoryModel itemCategoryModel = new ItemCategoryModel(key);
    gson = new Gson();
    String itemString = gson.toJson(itemCategoryModel);
    Call<ItemCategoryResponse> callItem = apiService.getCategory(itemString);
    callItem.enqueue(new Callback<ItemCategoryResponse>() {
      @Override
      public void onResponse(@NonNull Call<ItemCategoryResponse> call, @NonNull Response<ItemCategoryResponse> response) {
        if (response.body() != null && response.body().getResponseCode().equals("1")) {
          mViewModel.insertAll(response.body().getData());
          itemCategoryDataList = mViewModel.getLiveItemCategories().getValue();
//          ArrayAdapter<ItemCategoryData> arrayAdapter = new ArrayAdapter<>( getContext(), android.R.layout.simple_dropdown_item_1line, itemCategoryDataList);
//          mainCategory.setAdapter(arrayAdapter);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ItemCategoryResponse> call, @NonNull Throwable t) {
        Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {
      if (data != null) {
        ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
        if (returnValue != null) {
          loadImage(returnValue);
        }

      }

    }else{ }
  }

  private void loadImage(ArrayList<String> returnValue) {
    layout = getActivity().findViewById(R.id.addImageLinearLayout);
    for(int i=0;i<returnValue.size();i++)
    {
      ImageView image = new ImageView(getActivity());
      image.setLayoutParams(new android.view.ViewGroup.LayoutParams(150,150));
      image.setPaddingRelative(4,4,4,4);
      image.setMaxHeight(100);
      image.setMaxWidth(100);
      image.setMinimumHeight(100);
      image.setMaxHeight(100);
      image.setScaleType(ImageView.ScaleType.FIT_XY);
      Bitmap theImage = BitmapFactory.decodeFile(returnValue.get(i));
      image.setImageBitmap(theImage);
//      imageArray.add(theImage);
      // Adds the view to the layout
      layout.addView(image);
      imagePaths.add(returnValue.get(i));

    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          Pix.start(getActivity(), Options.init().setRequestCode(100));
        } else {
          Toast.makeText(getContext(), "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
        }
        break;
      }
    }
  }

  public void addItem() {
    String name = "ghsdasdADukjqwqsdsswwswweweadswsdsdAeerwrwdssdw ssasd s  wdewwew fsdf asdasf chasdasd sda";
    String desc = "newaersdasADSjhjkwewedeqwqsdsdsdsdssdsdwwwerwrwsfsdfwwdsqa ASasaA  asdqwqwv sf  asdas afas sdasda";
    String color = "1";
    String userId = "5";
    String categoryId = "5";
    String subId = "16";
    String country = "USA";
    String address = "heaven lane nigeria";
    String latitude = "40s";
    String longitude = "22w";

    ItemModel itemModel = new ItemModel(userId, name, color, country, address, longitude, latitude, desc, categoryId, subId);
    String itemString = gson.toJson(itemModel);

    Log.i("Item", itemString);
    Call<List<AddItemResponse>> call = apiService.addItem(itemString);
    call.enqueue(new Callback<List<AddItemResponse>>() {
      @Override
      public void onResponse(@NonNull Call<List<AddItemResponse>> call, @NonNull Response<List<AddItemResponse>> response) {
        Log.i("RES", response.body().get(0).getResponseCode());
        if (response.body() != null && response.body().get(0).getResponseCode().equals("1")){
          AddItemResponseData data = response.body().get(0).getData();
          String id = data.getRecord();
          Log.i("ID", id);
          for (int i = 0; i < imagePaths.size(); i++){
            Log.i("USER", id);
            uploadImage(imagePaths.get(i), id);
          }
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<AddItemResponse>> call, @NonNull Throwable t) {
        Toast.makeText(getContext(), "Check your network", Toast.LENGTH_LONG).show();
      }
    });

  }

  private void uploadImage(String path, String id) {
    File file = new File(path);

//    File file = FileUtils.getFile( getContext(), Uri.parse(path));

    //Request body
    RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);

    MultipartBody.Part part = MultipartBody.Part.createFormData("file[]", file.getName(), fileReqBody);

    RequestBody description = RequestBody.create(MediaType.parse("text/plain"), file.getName());

    Call<List<AddItemResponse>> call = apiService.uploadImage(part, description, id);
    call.enqueue(new Callback<List<AddItemResponse>>() {
      @Override
      public void onResponse(@NonNull Call<List<AddItemResponse>> call,@NonNull Response<List<AddItemResponse>> response) {
        String message = response.body().get(0).getResponseCode();
        Log.i("MESSAGE", response.body().get(0).getResponseCode());
        if (response.body() != null)
        Log.i("RES", response.body().get(0).getResponseStatus());
      }

      @Override
      public void onFailure(@NonNull Call<List<AddItemResponse>> call, @NonNull Throwable t) {
        Log.i("ERROR", t.toString());
      }
    });
    Log.i("WE", "HERE");
  }
}
