package com.example.easyprivate;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;


import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.Absen;
import com.example.easyprivate.model.Pemesanan;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class CustomUtility {
    private Context mContext;
    private Geocoder mGeocoder;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface = rci.getApiInterface();
    private static final String TAG = "CustomUtility";

    public CustomUtility(Context mContext) {
        this.mContext = mContext;
        this.mGeocoder = new Geocoder(mContext, Locale.getDefault());
    }

    public Address getAddress(Double lat, Double lon){
        List<Address> addressList = null;
        try {
            addressList = mGeocoder.getFromLocation(lat, lon, 1);
            if (addressList != null && addressList.size() > 0){
                return addressList.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap encodeToQrCode(String s, Integer qrSize){
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, qrSize, qrSize, null);
        }catch(WriterException e){
            Log.d(TAG, "encodeBitmap: "+e.getMessage());
            return null;
        }

        int w = bitMatrix.getWidth(), h = bitMatrix.getHeight();
        int[] pixels = new int[w*h];

        for(int y = 0; y<h; y++){
            int offset = y*w;
            for (int x = 0; x<w; x++){
                pixels[offset + x] = bitMatrix.get(x, y)? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, qrSize, 0,0,w,h);

        return bitmap;
    }

    public String reformatDateTime(String dateTimeStr, String beforePattern, String afterPattern){
        SimpleDateFormat sdf = new SimpleDateFormat(beforePattern);
        try{
            Date date = sdf.parse(dateTimeStr);

            sdf.applyPattern(afterPattern);
            String reformatedStr = sdf.format(date);

            return reformatedStr;
        }catch (ParseException e){
            Log.d(TAG, "reformatDateTime: "+e.getMessage());
            return "";
        }
    }

    public Absen jsonToAbsen(String absenJsonStr){
        Gson gson = new Gson();
        try {
            Absen absen = gson.fromJson(absenJsonStr, Absen.class);
            return absen;
        }catch (Throwable t){
            Log.d(TAG, "jsonToAbsen: "+t.getMessage());
            return null;
        }
    }

    public String pemesananToJson(Pemesanan pemesanan){
        String jsonStr = new Gson().toJson(pemesanan);
        Log.d(TAG, "pemesananToJson: "+jsonStr);
        return jsonStr;
    }

    public void putIntoImage(String avatarStr, CircleImageView civ){
        if (avatarStr == null||avatarStr.equals("")){
            civ.setImageResource(R.drawable.account_default);
            return;
        }
        Picasso.get()
                .load(avatarStr)
                .placeholder(R.drawable.account_default)
                .error(R.drawable.account_default)
                .noFade()
                .into(civ, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                        String avatarStrAlt = modifyAvatarStr(avatarStr);
                        putIntoImageAlt(avatarStrAlt, civ);
                    }
                });
    }

    public void putIntoImageAlt(String avatarStr, CircleImageView civ){
        Picasso.get()
                .load(avatarStr)
                .placeholder(R.drawable.account_default)
                .error(R.drawable.account_default)
                .noFade()
                .into(civ);
    }

    public String modifyAvatarStr(String avatarStr){
        avatarStr = rci.getBaseUrl() + "assets/avatars/" + avatarStr;
        return avatarStr;
    }
}
