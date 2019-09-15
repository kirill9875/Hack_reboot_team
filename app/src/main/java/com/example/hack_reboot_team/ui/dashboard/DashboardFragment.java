package com.example.hack_reboot_team.ui.dashboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.DBhelper;
import com.example.hack_reboot_team.R;

import java.util.Random;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    LinearLayout l;

    DBhelper dbHelper;
    SQLiteDatabase DB;
    Button add_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        add_btn = (Button)root.findViewById(R.id.angry_btn_dash);

        add_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add_profile();
            }
        });
        return root;
    }

    private void add_profile() {
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
        tName.setText("Имя");
        tName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tName.setTextColor(Color.parseColor("#333333"));
        tName.setTypeface(typeface);
        tName.setGravity(Gravity.LEFT);


        Button b = new Button(this.getContext());
        b.setLayoutParams(params3);
        b.setText("Add");
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int idimg[] = {R.mipmap.ben, R.mipmap.den, R.mipmap.git, R.mipmap.pig};

                LinearLayout parent = (LinearLayout) c.getParent();
                parent.removeViewAt(parent.getChildCount()-1);
                int min = 0;
                int max = 3;
                int diff = max - min;
                Random random = new Random();
                int i = random.nextInt(diff + 1);
                i += min;
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBhelper.NAME,etName.getText().toString());
                contentValues.put(DBhelper.STATYS,"invited");
                contentValues.put(DBhelper.IMGID,idimg[i]);
                contentValues.put(DBhelper.QRCHECK,true);


                long id = DB.insert(DBhelper.TABLE_NAME_USER, null, contentValues);
                System.out.print("Занесено в табл " + id + '\n');
                addcard(etName.getText().toString(),"invited",idimg[i], true, (int)id);
            }
        });

        l = (LinearLayout)getView().findViewById(R.id.linerForCardProfile);

        l1.addView(etName);
        l1.addView(tName);
        l1.addView(b);
        c.addView(l1);
        l.addView(c);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConnectDB();

        Cursor cursor = DB.query(DBhelper.TABLE_NAME_USER, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idimg[] = {R.mipmap.ben, R.mipmap.den, R.mipmap.git, R.mipmap.pig};
            int arr[] = {0,0,1,1};
            do {
                addcard(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("statys")), cursor.getInt(cursor.getColumnIndex("img_id")),cursor.getInt(cursor.getColumnIndex("qr_check")) > 0,cursor.getInt(cursor.getColumnIndex("id")));
            }while (cursor.moveToNext());

        } else {
            addnullcard();
        }
    }

    private void ConnectDB() {
        dbHelper = new DBhelper(this.getContext());
        DB = dbHelper.getWritableDatabase();
    }

    void addcard(String name, String type, int img, boolean qrcheck, final int userid){
        CardView c = new CardView(this.getContext());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params2.setMargins(15,15,15,15);

        c.setLayoutParams(params2);
        c.setRadius(40);
        c.setContentPadding(50, 25, 50, 25);

        c.setCardBackgroundColor(Color.parseColor("#ffffff"));
        c.setMaxCardElevation(5);


        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(15,15,15,15);
        LinearLayout l1 = new LinearLayout(this.getContext());
        l1.setLayoutParams(params);
        l1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout l2 = new LinearLayout(this.getContext());
        l2.setLayoutParams(params2);
        l2.setOrientation(LinearLayout.VERTICAL);

        LinearLayout l3 = new LinearLayout(this.getContext());
        l3.setLayoutParams(params2);
        l3.setOrientation(LinearLayout.VERTICAL);

        LinearLayout l4 = new LinearLayout(this.getContext());
        l4.setLayoutParams(params);
        l4.setOrientation(LinearLayout.VERTICAL);
        l4.setGravity(Gravity.RIGHT);


        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params5.setMargins(0,0,0,15);
        TextView tName = new TextView(this.getContext());
        tName.setLayoutParams(params5);
        tName.setText(name);
        tName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tName.setTextColor(Color.parseColor("#333333"));
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.roboto_bold);
        tName.setTypeface(typeface);

        params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tPrice = new TextView(this.getContext());
        tPrice.setLayoutParams(params5);

        tPrice.setText(type);

        tPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        tPrice.setTextColor(Color.parseColor("#8080FF"));
        tPrice.setGravity(Gravity.RIGHT);
        tPrice.setTypeface(typeface);


        params5 = new LinearLayout.LayoutParams(
                200, 200);
        params5.setMargins(10,0,10,10);
        ImageView i1 = new ImageView(this.getContext());
        i1.setLayoutParams(params5);
        i1.setImageResource(img);


        params2.setMargins(20, 0,0,0);
        Switch s = new Switch(this.getContext());
        s.setLayoutParams(params2);
        s.setText("QR/Счет");
        s.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        s.setHighlightColor(Color.parseColor("#FF7EA8"));
        s.setTextColor(Color.parseColor("#333333"));
        s.setTypeface(typeface);
        s.setChecked(qrcheck);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int b = 0;
                if(isChecked){b = 1;}
               DB.execSQL("UPDATE "+dbHelper.TABLE_NAME_USER+" SET "+ dbHelper.QRCHECK +" = "+ b + " WHERE id = "+userid);
            }
        });


        l = (LinearLayout)getView().findViewById(R.id.linerForCardProfile);

        l4.addView(s);
        l3.addView(tName);
        l3.addView(tPrice);
        l2.addView(i1);
        l1.addView(l2);
        l1.addView(l3);
        l1.addView(l4);
        c.addView(l1);
        l.addView(c);
    }


    void addnullcard(){
        CardView c = new CardView(this.getContext());

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params3.setMargins(15,15,15,15);

        c.setLayoutParams(params3);
        c.setRadius(40);
        c.setContentPadding(50, 25, 50, 25);

        c.setCardBackgroundColor(Color.parseColor("#ffffff"));
        c.setMaxCardElevation(5);



        LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params5.setMargins(0,0,0,15);
        TextView tName = new TextView(this.getContext());
        tName.setLayoutParams(params5);
        tName.setText("Пользователей пока нет, но вы можете их добавить :)");
        tName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tName.setTextColor(Color.parseColor("#333333"));
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.roboto_bold);
        tName.setTypeface(typeface);
        tName.setGravity(Gravity.CENTER);



        l = (LinearLayout)getView().findViewById(R.id.linerforcards);


        c.addView(tName);
        l.addView(c);
    }

}