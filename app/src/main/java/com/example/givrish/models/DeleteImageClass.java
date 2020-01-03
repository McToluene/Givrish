package com.example.givrish.models;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DeleteImageClass implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v instanceof ImageView){
                ((ViewGroup) v.getParent()).removeView(v);
            }
        }
}
