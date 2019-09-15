package com.example.hack_reboot_team.ui.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.DBhelper;
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
import static java.lang.Thread.sleep;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    LinearLayout l;
    TextView tv;
    private Button plus_btn;
    private Button add_btn;
    int num_product = 0;
    int Total_price = 0;

    public String keySession;
//    OkHttpClient client = new OkHttpClient();
    private final OkHttpClient client = new OkHttpClient();


    DBhelper dbHelper;
    SQLiteDatabase DB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        plus_btn = (Button) root.findViewById(R.id.angry_btn3);
        add_btn = (Button) root.findViewById(R.id.angry_btn);


        try {
            run();
            System.out.println("я тут был");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("я тут был2");

        }

        try {

            ();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQR();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_product();
            }
        });
        return root;
    }
    private void add_product() {
        if(num_product == 0){
            l = (LinearLayout)getView().findViewById(R.id.linerforcards);
            l.removeAllViews();
        }

        final CardView c = new CardView(this.getContext());

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params3.setMargins(15,15,15,15);

        c.setLayoutParams(params3);
        c.setRadius(40);
        c.setContentPadding(50, 25, 50, 25);

        c.setCardBackgroundColor(Color.parseColor("#ffffff"));
        c.setMaxCardElevation(5);

        LinearLayout l1 = new LinearLayout(this.getContext());
        l1.setLayoutParams(params3);
        l1.setOrientation(LinearLayout.VERTICAL);

        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.roboto_bold);
        params3.setMargins(0,0,0,0);
        final EditText etName = new EditText(this.getContext());
        etName.setLayoutParams(params3);
        etName.setPadding(15,15,15,15);
        etName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        etName.setTypeface(typeface);

        TextView tName = new TextView(this.getContext());
        tName.setLayoutParams(params3);
        tName.setText("Наименование");
        tName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tName.setTextColor(Color.parseColor("#333333"));
        tName.setTypeface(typeface);
        tName.setGravity(Gravity.LEFT);

        params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout l2 = new LinearLayout(this.getContext());
        l2.setLayoutParams(params3);
        l2.setOrientation(LinearLayout.HORIZONTAL);

        params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout l3 = new LinearLayout(this.getContext());
        l3.setLayoutParams(params3);
        l3.setOrientation(LinearLayout.VERTICAL);

        final EditText etCount = new EditText(this.getContext());
        etCount.setLayoutParams(params3);
        etCount.setPadding(15,15,15,15);
        etCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        etCount.setTypeface(typeface);

        TextView tCount = new TextView(this.getContext());
        tCount.setLayoutParams(params3);
        tCount.setText("Колчествово");
        tCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tCount.setTextColor(Color.parseColor("#333333"));
        tCount.setTypeface(typeface);
        tCount.setGravity(Gravity.LEFT);

        LinearLayout l4 = new LinearLayout(this.getContext());
        l4.setLayoutParams(params3);
        l4.setOrientation(LinearLayout.VERTICAL);

        final EditText etPrice = new EditText(this.getContext());
        etPrice.setLayoutParams(params3);
        etPrice.setPadding(15,15,15,15);
        etPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        etPrice.setTypeface(typeface);

        TextView tPrice = new TextView(this.getContext());
        tPrice.setLayoutParams(params3);
        tPrice.setText("Цена                   ");
        tPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tPrice.setTextColor(Color.parseColor("#333333"));
        tPrice.setTypeface(typeface);
        tPrice.setGravity(Gravity.LEFT);

        LinearLayout l5 = new LinearLayout(this.getContext());
        l5.setLayoutParams(params3);
        l5.setOrientation(LinearLayout.VERTICAL);
        l5.setGravity(Gravity.RIGHT);

        Button b = new Button(this.getContext());
        b.setLayoutParams(params3);
        b.setText("Add");
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Cursor cur = DB.rawQuery("SELECT "+dbHelper.IMGID+",id FROM "+dbHelper.TABLE_NAME_USER, new String[] {});
                ArrayList<Integer> personimgid = new ArrayList<>();
                ArrayList<Integer> usersid = new ArrayList<>();
                if (cur.moveToFirst()) {
                    do{
                        personimgid.add(cur.getInt(cur.getColumnIndex("img_id")));
                        usersid.add(cur.getInt(cur.getColumnIndex("id")));
                    }while (cur.moveToNext());
                }

                int arrcon[] = new int[personimgid.size()];
                for (int i = 0; i < personimgid.size(); i++){
                    arrcon[i]=0;
                }

                LinearLayout parent = (LinearLayout) c.getParent();
                parent.removeViewAt(parent.getChildCount()-1);
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBhelper.NAME,etName.getText().toString());
                contentValues.put(DBhelper.PRICE,etPrice.getText().toString());


                long id = DB.insert(DBhelper.TABLE_NAME_PRODUCT, null, contentValues);
                System.out.print("Занесено в табл " + id + '\n');
                addcard(etName.getText().toString(),"+ $" + etPrice.getText().toString(),arrcon,personimgid,usersid,(int)id);
                num_product++;
                Total_price += Integer.parseInt(etPrice.getText().toString());
                setTotalPrice(Total_price);
            }
        });

        l = (LinearLayout)getView().findViewById(R.id.linerforcards);


        l5.addView(b);
        l4.addView(etPrice);
        l4.addView(tPrice);
        l3.addView(etCount);
        l3.addView(tCount);
        l2.addView(l3);
        l2.addView(l4);
        l2.addView(l5);
        l1.addView(etName);
        l1.addView(tName);
        l1.addView(l2);
        c.addView(l1);
        l.addView(c);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConnectDB();

        Cursor cursor = DB.query(DBhelper.TABLE_NAME_PRODUCT, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idimg[] = {R.mipmap.ben, R.mipmap.den, R.mipmap.git, R.mipmap.pig};

            do {
                Cursor c = DB.rawQuery("SELECT "+dbHelper.IMGID+", id FROM "+dbHelper.TABLE_NAME_USER, new String[] {});
                ArrayList<Integer> personimgid = new ArrayList<>();
                ArrayList<Integer> usersid = new ArrayList<>();
                if (c.moveToFirst()) {
                    do{
                        personimgid.add(c.getInt(c.getColumnIndex("img_id")));
                        usersid.add(c.getInt(c.getColumnIndex("id")));
                    }while (c.moveToNext());
                }

                int arrcon[] = new int[personimgid.size()];

                for (int i = 0; i < personimgid.size(); i++){
                    arrcon[i]=0;
                }

                Cursor c2 = DB.rawQuery("SELECT id_user FROM "+dbHelper.TABLE_NAME_CON+"WHERE id_product="+Integer.toString(cursor.getInt(cursor.getColumnIndex("id"))), new String[] {});

                ArrayList<Integer> activeusersid = new ArrayList<>();
                if (c2.moveToFirst()) {
                    do{
                        activeusersid.add(c2.getInt(c2.getColumnIndex("id_user")));
                    }while (c2.moveToNext());
                    for(int i = 0; i < usersid.size(); i++){
                        for(int j = 0; j < activeusersid.size(); j++){
                            if (usersid.get(i) == activeusersid.get(j)){
                                arrcon[i] = 1;
                            }
                        }
                    }
                }


                addcard(cursor.getString(cursor.getColumnIndex("name")), "+ $"+cursor.getString(cursor.getColumnIndex("price")), arrcon, personimgid, usersid, cursor.getInt(cursor.getColumnIndex("id")));
                num_product++;
                Total_price+=Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                setTotalPrice(Total_price);
            }while (cursor.moveToNext());

        } else {
            addnullcard();
            setTotalPrice(0);
            num_product = 0;
            Total_price = 0;
        }
    }

    private void createAcc() throws JSONException {

        String val1 = "63d8fa480ed2c101ba0d76b9c793fe62e25032fd";
        String val2 = "e81dd1224666ae0146251ca5257fd36d19df9288";
        int sum = 1500;


        String js = "{\"amount\": " + Integer.toString(sum) +
                ",  \"currencyCode\": 810,  \"description\": \"test description\",  " +
                "\"number\": \"344fcb54-c81a-4ec6-a306-fc8dbd2d6167953cd861\", " +
                " \"payer\": \"" +
                val1 +
                "\",  \"recipient\": \"" +
                val2 + "\"}";

        System.out.println(js);

        JSONObject json = new JSONObject(js);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");


        RequestBody body = RequestBody.create(JSON, json.toString());

        try {
            sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder()
                .url("http://89.208.84.235:31080/api/v1/invoice")
                .addHeader("FPSID",keySession)
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




    }

    private void startQR() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    private void setTotalPrice(int count) {
        tv = (TextView) getView().findViewById(R.id.textTotalPriceCount);
        tv.setText("$"+Integer.toString(count));
    }

    private void ConnectDB() {
        dbHelper = new DBhelper(this.getContext());
        DB = dbHelper.getWritableDatabase();
    }

    void addnullcard() {
        CardView c = new CardView(this.getContext());

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params3.setMargins(15, 15, 15, 15);

        c.setLayoutParams(params3);
        c.setRadius(40);
        c.setContentPadding(50, 25, 50, 25);

        c.setCardBackgroundColor(Color.parseColor("#ffffff"));
        c.setMaxCardElevation(5);


        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params5.setMargins(0, 0, 0, 15);
        TextView tName = new TextView(this.getContext());
        tName.setLayoutParams(params5);
        tName.setText("Товаров пока нет, но вы можете их добавить :)");
        tName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tName.setTextColor(Color.parseColor("#333333"));
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.roboto_bold);
        tName.setTypeface(typeface);
        tName.setGravity(Gravity.CENTER);


        l = (LinearLayout) getView().findViewById(R.id.linerforcards);


        c.addView(tName);
        l.addView(c);
//    public Call post(String url, String json, Callback callback) {
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//        return call;
//    }

    }

    public void run() throws Exception {

        String js = "{ \"addresses\": [\"string\"],\"deviceId\": \"string\", \"deviceType\": 1 }";

        JSONObject json = new JSONObject(js);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");


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


    void addcard(String name, String price, int arr[], ArrayList<Integer> idimg, final ArrayList<Integer> usersid, final int card_id){
        CardView c = new CardView(this.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params3.setMargins(15,15,15,15);

        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params4.setMargins(15,15,15,15);

        c.setLayoutParams(params3);
        c.setRadius(40);
        c.setContentPadding(50, 25, 50, 25);

        c.setCardBackgroundColor(Color.parseColor("#ffffff"));
        c.setMaxCardElevation(5);


        LinearLayout l1 = new LinearLayout(this.getContext());
        l1.setLayoutParams(params);
        l1.setOrientation(LinearLayout.VERTICAL);

        LinearLayout l2 = new LinearLayout(this.getContext());
        l2.setLayoutParams(params);
        l2.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout l3 = new LinearLayout(this.getContext());
        l3.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        l3.setOrientation(LinearLayout.HORIZONTAL);



        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params5.setMargins(0,0,0,15);
        TextView tName = new TextView(this.getContext());
        tName.setLayoutParams(params5);
        tName.setText(name);
        tName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tName.setTextColor(Color.parseColor("#333333"));
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.roboto_bold);
        tName.setTypeface(typeface);

        params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tPrice = new TextView(this.getContext());
        tPrice.setLayoutParams(params5);
        tPrice.setText(price);
        tPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tPrice.setTextColor(Color.parseColor("#8080FF"));
        tPrice.setGravity(Gravity.RIGHT);
        tPrice.setTypeface(typeface);


        params5 = new LinearLayout.LayoutParams(
                150, 150);
        params5.setMargins(10,10,10,10);
        for(int i = 0; i < idimg.size(); i++){
            final ImageView i1 = new ImageView(this.getContext());
            i1.setLayoutParams(params5);
            i1.setImageResource(idimg.get(i));
            if(!(arr[i] == 1)) {
                i1.setAlpha(100);
            }
            final int finalI = i;
            i1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    setVis(i1, usersid.get(finalI), card_id);
                }
            });
            l3.addView(i1);
        }



        l = (LinearLayout)getView().findViewById(R.id.linerforcards);

        l2.addView(tName);
        l2.addView(tPrice);

        l1.addView(l2);
        l1.addView(l3);
        c.addView(l1);
        l.addView(c);
    }

    void setVis(ImageView i1, int user_id, int product_id){
        if(i1.getImageAlpha() == 100){
            i1.setAlpha(250);
            DB.execSQL("INSERT INTO "+dbHelper.TABLE_NAME_CON+" (id_user,id_product) VALUES ("+user_id+","+product_id+")");
        } else {
            i1.setAlpha(100);
            DB.execSQL("DELETE FROM "+dbHelper.TABLE_NAME_CON+" WHERE id_user = "+user_id+" AND id_product ="+product_id);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Log.d("lol", "Scanned: " + result.getContents());
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