package com.example.givrish.ui;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.givrish.ItemSubCategoryData;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemFragment extends Fragment {

private AddItemViewModel mViewModel;
private int RequestCode = 100;
private Options options;
private LinearLayout layout;
private ArrayList<String> returnValue = new ArrayList<>();
private List<ItemCategoryData> itemCategoryDataList;
private AppCompatSpinner mainCategory;
private AppCompatSpinner subCategory;
private AppCompatSpinner colorSpinner;
private  static final String apiKey= "com.example.givrish.ui.APIKEY";
private List<ItemSubCategoryData> itemSubCategoryDataList;
private TextInputLayout mainCategoryLayout;
private int selectedPosition;
private String SelectedCategory ="";

public static AddItemFragment newInstance() {
	return new AddItemFragment();
}

@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
						 @Nullable Bundle savedInstanceState) {
	return inflater.inflate(R.layout.add_item_fragment, container, false);
}

@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	mainCategory = (AppCompatSpinner) getActivity().findViewById(R.id.mainCategory);
	subCategory = (AppCompatSpinner) getActivity().findViewById(R.id.subCategory);
	colorSpinner = (AppCompatSpinner) getActivity().findViewById(R.id.ItemColor);
	mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
	// TODO: Use the ViewModel
	FloatingActionButton addButton = (FloatingActionButton) getActivity().findViewById(R.id.addImagebtn);
	apiCategoryList(apiKey);
	
	ArrayAdapter<ItemColor> colorArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, populateColor());
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
	mainCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			try {
					selectedPosition = position;
//					SelectedCategory = mainCategory.getItemAtPosition(position).toString();
					apiSubCategoryList(String.valueOf(selectedPosition+1));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		
		}
	});
	
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


private void apiSubCategoryList(final String selectedCategory) {
	
	
	
	ItemCategoryModel itemCategoryModel = new ItemCategoryModel(apiKey);
	final List<ItemSubCategoryData> SubCategoryOfCategory = new ArrayList<>();
	ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
	Gson gson = new Gson();
	String itemString = gson.toJson(itemCategoryModel);
	Call<ItemSubCategoryResponse> callItem = apiService.getSubCategory(itemString);
	callItem.enqueue(new Callback<ItemSubCategoryResponse>() {
		@Override
		public void onResponse(Call<ItemSubCategoryResponse> call, Response<ItemSubCategoryResponse> response) {
			if (response.body().getResponseCode().equals("1")) {
				itemSubCategoryDataList = response.body().getData();
				String getWhatINeed;
				
				for(int i = 0; i< itemSubCategoryDataList.size(); i++){
					getWhatINeed = itemSubCategoryDataList.get(i).getItem_category_id();
					if(i== itemSubCategoryDataList.size()) {
						break;
						
					}else {
						if (getWhatINeed.equals(selectedCategory)) {
							SubCategoryOfCategory.add(itemSubCategoryDataList.get(i));

						}
					}
					
				}
				
				ArrayAdapter<ItemSubCategoryData> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, SubCategoryOfCategory);
				arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
				subCategory.setAdapter(arrayAdapter);
			}
		}
		
		@Override
		public void onFailure(Call<ItemSubCategoryResponse> call, Throwable t) {
		
		}
	});
}

private void apiCategoryList(String key) {
	ItemCategoryModel itemCategoryModel = new ItemCategoryModel(key);
	ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
	Gson gson = new Gson();
	String itemString = gson.toJson(itemCategoryModel);
	Call<ItemCategoryResponse> callItem = apiService.getCategory(itemString);
	callItem.enqueue(new Callback<ItemCategoryResponse>() {
		@Override
		public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
			if (response.body().getResponseCode().equals("1")) {
				itemCategoryDataList = response.body().getData();
				ArrayAdapter<ItemCategoryData> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, itemCategoryDataList);
				arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
				mainCategory.setAdapter(arrayAdapter);
			}
		}
		
		@Override
		public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
		Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_SHORT).show();
		}
	});
}

@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {
			ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
			loadImage(returnValue);
			
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
		// Adds the view to the layout
		layout.addView(image);
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
			return;
		}
	}
}
}
