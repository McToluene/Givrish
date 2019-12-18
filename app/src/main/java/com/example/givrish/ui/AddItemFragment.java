package com.example.givrish.ui;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.givrish.R;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddItemFragment extends Fragment {

private AddItemViewModel mViewModel;
private int RequestCode = 100;
private Options options;
private LinearLayout layout;
private ArrayList<String> returnValue = new ArrayList<>();


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

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {
			ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
			loadImage(returnValue);
		}else{ }
	}

private void loadImage(ArrayList<String> returnValue) {
	
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
