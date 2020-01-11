package com.example.givrish.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
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
import com.example.givrish.UserDataPreference;
import com.example.givrish.database.Constants;
import com.example.givrish.interfaces.CallBackListener;
import com.example.givrish.interfaces.ItemSelectedListener;
import com.example.givrish.models.AddItemResponse;
import com.example.givrish.models.AddItemResponseData;
import com.example.givrish.models.ItemModel;
import com.example.givrish.models.ItemSubCategoryData;
import com.example.givrish.R;
import com.example.givrish.models.ItemCategoryData;
import com.example.givrish.models.ApiKey;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.models.ItemColor;
import com.example.givrish.models.ItemSubCategoryResponse;
import com.example.givrish.models.LocationClass;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
  private static final String apiKey = "com.example.givrish.ui.APIKEY";
  private List<ItemSubCategoryData> itemSubCategoryDataList;
  private ApiEndpointInterface apiService;
  private TextInputEditText itemName;
  private TextInputEditText itemDesc;
  private Gson gson;
  private List<String> imagePaths = new ArrayList<>();
  private FloatingActionButton addButton;
  private CallBackListener listener;
  private LocationClass locationClass;
  private LocationClass.LocationResult locationResult;
  private boolean check = false;
  private String[] locationData;
  private String categoryId;
  private String subId;
  private int imageCount = 5;
  private TextView clearImageSelection;
  private ItemModel itemModel;
  private AddItemViewModel addItemViewModel;


  public AddItemFragment() {
  }

  public static AddItemFragment newInstance() {
    return new AddItemFragment();
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof CallBackListener)
      listener = (CallBackListener) context;
  }


  public static AddItemFragment newParcelableInstance(ItemModel itemModel){
    Bundle args=new Bundle();
    args.putParcelable("msg", itemModel);
    AddItemFragment fragment=new AddItemFragment();
    fragment.setArguments(args);
    return fragment;
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
    colorSpinner = view.findViewById(R.id.ItemColor);
    itemName = view.findViewById(R.id.item_name);
    itemDesc = view.findViewById(R.id.item_desc);
    clearImageSelection = view.findViewById(R.id.clear_image_selection);

    addButton = view.findViewById(R.id.addImagebtn);

    toolbar.setTitle("Add Item");

    if(getActivity() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_icon);
    }

    if(getArguments()!=null){
      itemModel = getArguments().getParcelable("msg");
    }

    if(itemModel!=null) {
     itemName.setText(itemModel.getItem_title());
     itemDesc.setText(itemModel.getItem_description());
     //get all the rest
    }

    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);


    // TODO: Use the ViewModel
    locationClass=new LocationClass();
    displayLocation();

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


    clearImageSelection.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          clearImageFunction();
      }
    });
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        options = Options.init()
                .setRequestCode(100)
                .setCount(imageCount)
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
//          ItemCategoryData selectedItem =  itemCategoryDataList.get(position);
//          getSub(selectedItem);
          String selectedItem = null;
          for (int i=0; i<itemCategoryDataList.size();i++) {
            if(itemCategoryDataList.get(i).getItem_category_name().equals(mainCategory.getText().toString())|| itemCategoryDataList.get(i).getItem_category_name().equals(mainCategory.getText().toString()+" ")){
            selectedItem = itemCategoryDataList.get(i).getItem_category_id();
              categoryId = itemCategoryDataList.get(i).getItem_category_id();
                      getSub(selectedItem);
            break;}
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    subCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        subId = itemSubCategoryDataList.get(position).getItem_sub_category_id();
      }
    });
  }

    private void clearFieldsFunction() {
        itemName.setText(null);
        itemDesc.setText(null);
        mainCategory.setText(null);
        subCategory.setText(null);
        colorSpinner.setText(null);
        clearImageFunction();
    }
    private void clearImageFunction() {

        if (layout ==null){}else {layout.removeAllViews();}
        imageCount = 5;
        addButton.setEnabled(true);
        imagePaths.clear();
    }

    @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.add_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
        listener.onBackClick(Dashboard.ADD_ITEM_FRAGMENT_FLAG);
    }else if(item.getItemId() == R.id.menu_hamburger){
        clearFieldsFunction();
    }
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
    ApiKey itemCategoryModel = new ApiKey(apiKey);
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

  private void getSub(String selected) {
//    Log.i("SELECT", selected.getItem_category_id());
    final List<ItemSubCategoryData> subCategoryOfCategory = new ArrayList<>();
    for (int i = 0; i < itemSubCategoryDataList.size(); i++) {
      if (itemSubCategoryDataList.get(i).getItem_category_id().equals(selected)){
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
    ApiKey apiKey = new ApiKey(key);
    gson = new Gson();
    String itemString = gson.toJson(apiKey);
    Call<ItemCategoryResponse> callItem = apiService.getCategory(itemString);
    callItem.enqueue(new Callback<ItemCategoryResponse>() {
      @Override
      public void onResponse(@NonNull Call<ItemCategoryResponse> call, @NonNull Response<ItemCategoryResponse> response) {
        if (response.body() != null && response.body().getResponseCode().equals("1")) {
          mViewModel.insertAll(response.body().getData());
          itemCategoryDataList = mViewModel.getLiveItemCategories().getValue();
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
        imageCount = imageCount - returnValue.size();
        if (imageCount == 0) {
          addButton.setEnabled(false);
          Toast.makeText(getContext(), "Maximum number of image(s) selected", Toast.LENGTH_LONG).show();
        }
        if (returnValue != null) {
          loadImage(returnValue);
        }

      }

    }else{
      if(requestCode==123)
        Toast.makeText(getContext(), "phone permission granted", Toast.LENGTH_SHORT).show();
      displayLocation();

    }
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
      }case 1: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                  == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission
                  (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getContext(), "app location permission granted", Toast.LENGTH_SHORT).show();
            //if permission is granted
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 123);
          }
        } else {
          //app location not granted, it should ask for it again
          check = locationClass.getLocation(getContext(), locationResult);

          if (!check)
            //Ask for permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
      }
    }
  }

  public void addItem() {

    String name = itemName.getText().toString();
    String desc = itemDesc.getText().toString();
    String color = colorSpinner.getText().toString();
    String userId = UserDataPreference.getInstance(getContext()).retrievePreference(getString(R.string.user_id));

    if (!name.isEmpty() && !desc.isEmpty() && imagePaths.size()!=0) {
      //location[0] is country && location[1] is state && location[2] is address && location[3] is longitude && location[4] is latitude
      ItemModel itemModel = new ItemModel(userId, name, color, locationData[0], locationData[1], locationData[2], locationData[3], locationData[4], desc, categoryId, subId);

      String itemString = gson.toJson(itemModel);

      Call<List<AddItemResponse>> call = apiService.addItem(itemString);
      call.enqueue(new Callback<List<AddItemResponse>>() {
        @Override
        public void onResponse(@NonNull Call<List<AddItemResponse>> call, @NonNull Response<List<AddItemResponse>> response) {

          if (response.body() != null && response.body().get(0).getResponseCode().equals("1")) {
            Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_LONG).show();
            AddItemResponseData data = response.body().get(0).getData();
            String id = data.getRecord();
            Log.i("ID", id);
            for (int i = 0; i < imagePaths.size(); i++) {
              Log.i("USER", id);
              uploadImage(imagePaths.get(i), id);
            }
            clearFieldsFunction();
          }
        }

        @Override
        public void onFailure(@NonNull Call<List<AddItemResponse>> call, @NonNull Throwable t) {
          Toast.makeText(getContext(), "Check your network", Toast.LENGTH_LONG).show();
        }
      });

    } else {
      Toast.makeText(getContext(), "pls fill out all fields correctly", Toast.LENGTH_LONG).show();
    }

}

  private void uploadImage(String path, String id) {
    File file = new File(path);

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

  private void displayLocation() {

    locationResult = new LocationClass.LocationResult(){

      @Override
      public void gotLocation(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
          List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                  location.getLongitude(), 1);

          String country = addressList.get(0).getCountryName();
          String state = addressList.get(0).getAdminArea();
          String addressLine = addressList.get(0).getAddressLine(0);

          DecimalFormat df = new DecimalFormat("#.###");

          String lng = df.format(addressList.get(0).getLongitude());
          String lat = df.format(addressList.get(0).getLatitude());


          //try to use if statement for checking empty string
          country = country.equals("")  ? null : country;
          state = state.equals("") ? null : state;
          addressLine = addressLine .equals("") ? null : addressLine;
          lng = lng.equals("") ? null : lng;
          lat = lat.equals("") ? null : lat;
          String addr = country + "\\" + state + "\\" + addressLine + "\\" + lng + "\\" + lat;
          locationData = addr.split("\\\\");
//

        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    };

    check = locationClass.getLocation(getContext(), locationResult);

    if(!check)
      //Ask for permission
      ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

  }
}
