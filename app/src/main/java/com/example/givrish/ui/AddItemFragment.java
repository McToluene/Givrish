package com.example.givrish.ui;

import androidx.lifecycle.LifecycleObserver;
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

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.givrish.R;
import com.example.givrish.models.ItemCategoryModel;
import com.example.givrish.models.ItemCategoryResponse;
import com.example.givrish.network.ApiEndpointInterface;
import com.example.givrish.network.RetrofitClientInstance;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemFragment extends Fragment {

private AddItemViewModel mViewModel;
private int RequestCode = 100;
private Options options;
private LinearLayout layout;
private ArrayList<String> returnValue = new ArrayList<>();
private  static final String apiKey= "com.example.givrish.ui.APIKEY";


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
	mViewModel = ViewModelProviders.of(this).get(AddItemViewModel.class);
	// TODO: Use the ViewModel
	FloatingActionButton addButton = (FloatingActionButton) getActivity().findViewById(R.id.addImagebtn);
	apiResponseList(apiKey);
	
	
	
	
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
	
}

private void apiResponseList(String key) {
	ItemCategoryModel itemCategoryModel = new ItemCategoryModel(key);
	ApiEndpointInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiEndpointInterface.class);
	Gson gson = new Gson();
	String itemString = gson.toJson(itemCategoryModel);
	Call<ItemCategoryResponse> callItem = apiService.getCategory(itemString);
	callItem.enqueue(new Callback<ItemCategoryResponse>() {
		@Override
		public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
			if(response.body().getResponseCode().equals("1")){
				Toast.makeText(getContext(), response.body().getResponseStatus(), Toast.LENGTH_LONG).show();
			}
		}
		
		@Override
		public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
		
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
//	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.cell, returnValue);
//	GridView gridView = (GridView) getActivity().findViewById(R.id.addImageGrid);
//	arrayAdapter.setDropDownViewResource(R.layout.cell);
//	gridView.setAdapter(arrayAdapter);
	layout = (LinearLayout)getActivity().findViewById(R.id.addImageLinearLayout);
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
