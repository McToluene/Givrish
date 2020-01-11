package com.example.givrish.interfaces;

import androidx.fragment.app.Fragment;

public interface ItemSelectedListener {
  void loadItem(Fragment fragment, String tag);
  void onCloseFragment(String tag);
}
