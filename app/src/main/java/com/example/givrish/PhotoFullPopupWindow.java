package com.example.givrish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.palette.graphics.Palette;

import com.bumptech.glide.request.RequestListener;
import com.example.givrish.database.Constants;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PhotoFullPopupWindow extends PopupWindow {

    View view;
    Context mContext;
    PhotoView photoView;
    ProgressBar loading;
    ViewGroup parent;
    private static PhotoFullPopupWindow instance = null;



    public PhotoFullPopupWindow(Context ctx, int layout, View v, String imageUrl, Bitmap bitmap) {
        super(((LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate( R.layout.picture_fullscreen_layout, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = ctx;
        this.view = getContentView();
        ImageButton closeButton = (ImageButton) this.view.findViewById(R.id.ib_close);
        setOutsideTouchable(true);

        setFocusable(true);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dismiss();
            }
        });
        //---------Begin customising this popup--------------------

        photoView = (PhotoView) view.findViewById(R.id.image);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        // ImageUtils.setZoomable(imageView);
        //----------------------------
        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= 16) {
                parent.setBackground(new BitmapDrawable(mContext.getResources(), Constants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));// ));
            } else {
                onPalette(Palette.from(bitmap).generate());

            }
            photoView.setImageBitmap(bitmap);
        } else {
            loading.setIndeterminate(true);
            loading.setVisibility(View.VISIBLE);
            loading.setIndeterminate(false);
            loading.setBackgroundColor(Color.LTGRAY);
            Picasso.get().load(imageUrl).error(R.drawable.defaultprofile).into(photoView);

            //photoView.setImageBitmap(resource);
            loading.setVisibility(View.GONE);
        }
        showAtLocation(v, Gravity.CENTER, 0, 0);
    }


    public void onPalette(Palette palette) {
        if (null != palette) {
            ViewGroup parent = (ViewGroup) photoView.getParent().getParent();
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
        }
    }

}
