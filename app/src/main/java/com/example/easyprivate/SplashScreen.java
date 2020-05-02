package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
private GoogleSignInAccount account;
    private static final String TAG = "SplashScreen";
    private Button retry;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface = rci.getApiInterface();

    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        account = GoogleSignIn.getLastSignedInAccount(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(account!=null){
                    muridValid();
                }else{
                    Intent i = new Intent(SplashScreen.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);

        retry = findViewById(R.id.btnRetry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account!=null){
                    muridValid();
                }else{
                    Intent i = new Intent(SplashScreen.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void callUser(){
        Call<User> call = apiInterface.getMurid(account.getEmail());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()) {
                    return;
                }
                User murid = response.body();
                UserHelper uh = new UserHelper(SplashScreen.this);
                uh.storeUser(murid);
                Intent intent = new Intent(SplashScreen.this,MainNavigation.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
    private void muridValid(){
        Call<Integer> call = apiInterface.getMuridValid(account.getEmail());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                int valid = response.body();
                if (valid ==1 ){
                    callUser();
                }
                else if(valid == 0){
                    Intent i = new Intent(SplashScreen.this, SignUpActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(SplashScreen.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });

    }
}
