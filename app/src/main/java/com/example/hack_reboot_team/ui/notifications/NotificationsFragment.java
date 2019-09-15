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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        generateQR();

        reqCode();


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

    private void generateQR() {

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("{\"invoiceId\":\"42615e75-6cde-41dc-a2fe-24e99d92c1c3\",\"amount\":58," +
                    "\"address\":\"537c8cf34d8c59d2c1341c1dd90f3a991c69c5fb\",\"currencyCode\":810}", BarcodeFormat.QR_CODE, 400, 400);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {
        }

    }
}