package com.example.easyprivate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyprivate.adapter.AbsenPembayaranAdapter;
import com.example.easyprivate.adapter.DetailAbsenPembayaranAdapter;
import com.example.easyprivate.api.ApiInterface;
import com.example.easyprivate.api.RetrofitClientInstance;
import com.example.easyprivate.model.MidtransPembayaran;
import com.example.easyprivate.model.PembayaranAbsen;
import com.midtrans.sdk.corekit.BuildConfig;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.nio.channels.FileLock;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaran extends AppCompatActivity implements TransactionFinishedCallback {
    RecyclerView rvList;
    private static final String BASE_URL=RetrofitClientInstance.BASE_URL+"api/midtrans/";
    private static final String CLIENT_KEY="SB-Mid-client-JFggDG5pgc2T9Z7c";
    Button bayarBT;
    TextView total;
    private RetrofitClientInstance rci = new RetrofitClientInstance();
    private ApiInterface apiInterface= rci.getApiInterface();
    private Context mContext;
    UserHelper uh;
    CustomUtility cu;
    Intent intent;
    ArrayList<PembayaranAbsen> pembayaranAbsens =new ArrayList<>();
    MidtransPembayaran midtransPembayaran = new MidtransPembayaran();
    private static final String TAG = "DetailPembayaran";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);
        init();
        callDetailPembayaran();

    }

    private void callTokenPembayaran() {
                MidtransSDK.getInstance().setTransactionRequest(transactionRequest());
                MidtransSDK.getInstance().startPaymentUiFlow(DetailPembayaran.this);
    }

    private void init() {
        rvList = findViewById(R.id.rvList);
        total=findViewById(R.id.totalbyr);
        bayarBT = findViewById(R.id.bayarBT);
        intent = getIntent();
        cu = new CustomUtility(this);
        uh = new UserHelper(this);
    }
    private void callDetailPembayaran() {
        Call<ArrayList<PembayaranAbsen>> absenpem = apiInterface.pemAbsen(uh.retrieveUser().getId(),
                null,
                null,
                intent.getIntExtra("bulan",0),
                intent.getIntExtra("tahun",0),
                null,
                null);
        absenpem.enqueue(new Callback<ArrayList<PembayaranAbsen>>() {
            @Override
            public void onResponse(Call<ArrayList<PembayaranAbsen>> call, Response<ArrayList<PembayaranAbsen>> response) {
                Log.d(TAG, "onResponse: "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
                pembayaranAbsens = response.body();
                DetailAbsenPembayaranAdapter pa = new DetailAbsenPembayaranAdapter(DetailPembayaran.this,pembayaranAbsens);
                rvList.setAdapter(pa);
                rvList.setLayoutManager(new LinearLayoutManager(mContext));
                int totalByr=0;
                for (int i =0;i<pembayaranAbsens.size();i++){
                    PembayaranAbsen pabs=pembayaranAbsens.get(i);
                    int jumper,harga;
                    jumper = pabs.getJumlahAbsen();
                    harga=pabs.getPemesanan().getMataPelajaran().getJenjang().getHargaPerPertemuan();
                    totalByr+= jumper*harga;
                }
                total.setText("Rp "+totalByr);
                bayarBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callTokenPembayaran();

                    }
                });
                makePayment();
            }

            @Override
            public void onFailure(Call<ArrayList<PembayaranAbsen>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
    public TransactionRequest transactionRequest(){
        TransactionRequest request =  new TransactionRequest(System.currentTimeMillis() + " " , 2000 );
        request.setCustomerDetails(customerDetails());
        ArrayList<ItemDetails> itemDetails = new ArrayList<>();
        for (int i=0; i<pembayaranAbsens.size(); i++){
            ItemDetails details = new ItemDetails(
                   String.valueOf( pembayaranAbsens.get(i).getIdPemesanan()),
                    pembayaranAbsens.get(i).getPemesanan().getMataPelajaran().getJenjang().getHargaPerPertemuan(),
                    pembayaranAbsens.get(i).getJumlahAbsen(),
                    "Pembelajaran "+pembayaranAbsens.get(i).getPemesanan().getMataPelajaran().getNamaMapel()+" dengan "+
                    pembayaranAbsens.get(i).getGuru().getName());
            itemDetails.add(details);
        }
        request.setItemDetails(itemDetails);
        CreditCard creditCard = new CreditCard();
        creditCard.setSaveCard(false);
        creditCard.setAuthentication(CreditCard.AUTHENTICATION_TYPE_RBA);
        request.setCreditCard(creditCard);
        return request;
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if(result.getResponse() != null){
            switch (result.getStatus()){
                case TransactionResult.STATUS_SUCCESS:
                    PembayaranActivity.pembayaranActivity.finish();
                    Toast.makeText(this, "Transaction Sukses " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();

            callStorePembayaran(result.getResponse());

        }else if(result.isTransactionCanceled()){
            Toast.makeText(this, "Transaction Failed", Toast.LENGTH_LONG).show();
        }else{
            if(result.getStatus().equalsIgnoreCase((TransactionResult.STATUS_INVALID))){
                Toast.makeText(this, "Transaction Invalid" + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Something Wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callStorePembayaran(TransactionResponse tr) {
       Integer bulan = pembayaranAbsens.get(0).getBulan();
       Integer tahun =pembayaranAbsens.get(0).getTahun();
       Float total = Float.parseFloat(tr.getGrossAmount());
       Integer totalInt=Math.round(total);
        Call<Void> pemStore = apiInterface.pembayaranStore(tr.getTransactionId(),
                uh.retrieveUser().getId(),
                tr.getOrderId(),
                tr.getStatusCode(),
                totalInt,
                tr.getTransactionTime(),
                bulan,
                tahun,
                tr.getRedirectUrl());
        pemStore.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "onResponse: pem store = "+response.message());
                if (!response.isSuccessful()){
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: pem store = "+t.getMessage());
            }
        });
    }

    private void makePayment(){
        SdkUIFlowBuilder.init()
                .setContext(this)
                .setMerchantBaseUrl(BASE_URL)
                .setClientKey(CLIENT_KEY)
                .setTransactionFinishedCallback(this)
                .enableLog(true)
                .setColorTheme(new CustomColorTheme("#777777","#f77474" , "#3f0d0d"))
                .buildSDK();
    }
    public CustomerDetails customerDetails(){
        CustomerDetails cd = new CustomerDetails();
        cd.setFirstName(uh.retrieveUser().getName());
        cd.setEmail(uh.retrieveUser().getEmail());
        cd.setPhone(uh.retrieveUser().getNoHandphone());
        return cd;
    }


}
