package com.example.easyprivate.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyprivate.CustomUtility;
import com.example.easyprivate.SignInActivity;
import com.example.easyprivate.R;

import com.example.easyprivate.UserHelper;
import com.example.easyprivate.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {
    TextView tvNama,tvEmail;
    Button btnSignOut;
    CircleImageView fotoCIV;
    GoogleSignInAccount account;
    Context mContext;
    private UserHelper uh;
    private User user;
    private CustomUtility cu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container, false);

        init(v);
        account = GoogleSignIn.getLastSignedInAccount(mContext);
        getUser();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return v;

    }

    private void init(View v){
        tvEmail = v.findViewById(R.id.tvEmail);
        tvNama = v.findViewById(R.id.tvNama);
        fotoCIV = v.findViewById(R.id.civProfilePic);
        btnSignOut = v.findViewById(R.id.btnSignOut);
        mContext = v.getContext();
        uh = new UserHelper(mContext);
        user = uh.retrieveUser();
        cu = new CustomUtility(mContext);
    }

    private void signOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(mContext, gso);
        client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(mContext, "Sign out successful", Toast.LENGTH_LONG).show();
                Intent i = new Intent(mContext, SignInActivity.class);
                mContext.startActivity(i);
                getActivity().finish();
            }
        });
    }

    private void getUser(){
        tvEmail.setText(user.getEmail());
        tvNama.setText(user.getName());
        if(user.getAvatar() != null) {
            cu.putIntoImage(user.getAvatar(), fotoCIV);
        }
    }

}
