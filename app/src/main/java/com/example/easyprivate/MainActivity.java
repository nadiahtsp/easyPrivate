package com.example.easyprivate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    GoogleSignInOptions gfo;
    int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gfo = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gfo);

        signInButton= findViewById(R.id.btn_sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            Intent status = new Intent(MainActivity.this, MainNavigation
                    .class);
            startActivity(status);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                account = task.getResult(ApiException.class);
               Intent mAccount = new Intent(MainActivity.this,MainNavigation.class);
               startActivity(mAccount);
            }
            catch (ApiException e){
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
}
