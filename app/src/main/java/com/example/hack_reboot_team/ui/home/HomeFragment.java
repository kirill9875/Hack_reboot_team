package com.example.hack_reboot_team.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button plus_btn;

    public String keySession;
//    OkHttpClient client = new OkHttpClient();
    private final OkHttpClient client = new OkHttpClient();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        plus_btn = (Button) root.findViewById(R.id.angry_btn3);

        createAcc();

        plus_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startQR();

            }
        });

        try {
            run();
            System.out.println("я тут был");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("я тут был2");

        }
        String user[] = {"Петя","Вася"};
        item w1 = new item("Potato", 32,2, user);
        item w2 = new item("Taxi", 86,1, user);

        item arr[] = {w1,w2};


        return root;
    }

    private void createAcc() {

        String val1 = "63d8fa480ed2c101ba0d76b9c793fe62e25032fd";
        String val2 = "e81dd1224666ae0146251ca5257fd36d19df9288";
        int sum = 1500;


        String json = "{\"amount\": " + Integer.toString(sum) +
                ",  \"currencyCode\": 810,  \"description\": \"test description\",  " +
                "\"number\": \"344fcb54-c81a-4ec6-a306-fc8dbd2d6167953cd861\", " +
                " \"payer\": \"" +
                val1 +
                "\",  \"recipient\": \"" +
                val2 + "\"}";


        System.out.println(json);

    }

    private void startQR() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();


//
//        post("http://httpbin.org/get", "", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // Something went wrong
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responseStr = response.body().string();
//                    System.out.println(responseStr);
//                    // Do what you want to do with the response.
//                } else {
//                    // Request not successful
//                }
//            }
//        });

    }


//    public Call post(String url, String json, Callback callback) {
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//        return call;
//    }



    public void run() throws Exception {

        String js = "{ \"addresses\": [\"string\"],\"deviceId\": \"string\", \"deviceType\": 1 }";

        JSONObject json = new JSONObject(js);
//        json.put("addresses","[]");
//        json.put("deviceId","test_device_id");
//        json.put("deviceType",1);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");



//        RquestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("addresses", "[]")
//                .addFormDataPart("deviceId","test_device_id")
//                .addFormDataPart("deviceType", String.valueOf(1))
//                .build();

        RequestBody body = RequestBody.create(JSON, json.toString());


        Request request = new Request.Builder()
                .url("http://89.208.84.235:31080/api/v1/session")
                .post(body)
                .build();
        System.out.println("я тут был3");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//                System.out.println(response.body().string());

                System.out.println("я тут был4");

                JSONObject jsRes = null;
                try {
                    jsRes = new JSONObject(response.body().string());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(jsRes.toString());
                try {
                    keySession =  jsRes.getString("data");
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    private class item {
        public String name;
        public float price;
        public int count;
        public String[] users;

        item(String name, float price, int count, String[] users){
            this.name = name;
            this.price = price;
            this.count = count;
            this.users = users;
        }
    }
}