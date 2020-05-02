package com.example.easyprivate.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.100.119:81/easyprivate/public/";

    public ApiInterface getApiInterface(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL+"api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        return  apiInterface;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }


}
