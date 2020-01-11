package com.example.givrish.models;

import com.google.android.gms.common.api.Api;

public class ApiKey {
    private String api_key;

    private  String user_id;

    public ApiKey(String api_key) {
        this.api_key = api_key;
    }

    public ApiKey(String api_key, String user_id) {
        this.api_key=api_key;
        this.user_id=user_id;
    }


}
