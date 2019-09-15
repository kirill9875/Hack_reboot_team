package com.example.hack_reboot_team.ui.notifications;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.R;
import com.example.hack_reboot_team.ui.home.HomeFragment;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private ImageView imageViewQrCode;
    OkHttpClient client = new OkHttpClient();


//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        imageViewQrCode = (ImageView) root.findViewById(R.id.qrCode);
        try {
            createAcc(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        reqCode();
        mockGenerateQR();


        return root;
    }

    public Call post_get(String url, String json, Callback callback) {

//        RequestBody body = RequestBody.create(JSON, json);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("somParam", "someValue")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }


    private void reqCode() {

        post_get("http://89.208.84.235:31080/api/v1/session", "", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    System.out.println(responseStr);
                    // Do what you want to do with the response.
                } else {
                    // Request not successful
                }
            }
        });

    }




    private String createAcc(final boolean qr) throws JSONException {

        final String sss = HomeFragment.getKeySession();

        final String uniqueID = UUID.randomUUID().toString();

        String val1 = "63d8fa480ed2c101ba0d76b9c793fe62e25032fd";
        String val2 = "e81dd1224666ae0146251ca5257fd36d19df9288";
        int sum = 1500;
        String js;

        if(qr){
             js = "{\"amount\": " + Integer.toString(sum) +
                    ",  \"currencyCode\": 810,  \"description\": \"test description\",  " +
                    "\"number\": \"" +
                    uniqueID +
                    "\", " +
                    " \"payer\": \"" +
                    val1 +
                    "\",  \"recipient\": \"" +
                    val2 + "\"}";
        } else {
             js = "{\"amount\":1500,  \"currencyCode\":810, " +
                    " \"description\": \"test description\"," +
                    "  \"number\": \"" +
                    uniqueID +
                    "\", " +
                    " \"recipient\": \"" +
                    val1 +
                    "\"}";
        }

        System.out.println(js);

        JSONObject json = new JSONObject(js);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");


        RequestBody body = RequestBody.create(JSON, json.toString());


        Request request = new Request.Builder()
                .url("http://89.208.84.235:31080/api/v1/invoice")
                .addHeader("FPSID",sss)
                .post(body)
                .build();
        System.out.println("я тут 3");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//                System.out.println(response.body().string());

                System.out.println("я тут4");

                JSONObject jsRes = null;
                try {
                    jsRes = new JSONObject(response.body().string());
                    System.out.println(jsRes);
                    if(qr){
                        generateQR(uniqueID);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(jsRes.toString());
                try {
//                    keySession =  jsRes.getString("data");
                    System.out.println(jsRes.getString("data"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println(e);
            }
        });

        return uniqueID;


    }



    private void generateQR(String in) {

        String uniqueID = UUID.randomUUID().toString();

        String otherAcc =
                "537c8cf34d8c59d2c1341c1dd90f3a991c69c5fb";

        String iluha = "63d8fa480ed2c101ba0d76b9c793fe62e25032fd";
        String kik = "e81dd1224666ae0146251ca5257fd36d19df9288";
        String invoice = "42615e75-6cde-41dc-a2fe-24e99d92c1c3";


        String cntx = "{\"invoiceId\":\"" +
                in +
                "\",\"amount\":1500," +
                "\"address\":\"" +
                iluha +
                "\",\"currencyCode\":810}";

        System.out.println(cntx);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(cntx, BarcodeFormat.QR_CODE, 400, 400);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {
        }

    }


    private void mockGenerateQR() {

        String uniqueID = UUID.randomUUID().toString();

        String otherAcc =
                "537c8cf34d8c59d2c1341c1dd90f3a991c69c5fb";

        String iluha = "63d8fa480ed2c101ba0d76b9c793fe62e25032fd";
        String kik = "e81dd1224666ae0146251ca5257fd36d19df9288";
        String invoice = "42615e75-6cde-41dc-a2fe-24e99d92c1c3";


        String cntx = "{\"invoiceId\":\"" +
                invoice +
                "\",\"amount\":1500," +
                "\"address\":\"" +
                otherAcc +
                "\",\"currencyCode\":810}";

        System.out.println(cntx);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(cntx, BarcodeFormat.QR_CODE, 400, 400);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {
        }

    }







}