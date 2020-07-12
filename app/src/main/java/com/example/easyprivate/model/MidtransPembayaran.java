package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MidtransPembayaran {

    @Expose
    @SerializedName("redirect_url")
    private String redirectUrl;
    @Expose
    @SerializedName("token")
    private String token;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
