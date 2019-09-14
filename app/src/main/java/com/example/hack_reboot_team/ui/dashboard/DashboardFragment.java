package com.example.hack_reboot_team.ui.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    LinearLayout l;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addcard("Вася", 0, R.mipmap.ben);
        addcard("Петя", 1, R.mipmap.den);
        addcard("Иван", 1, R.mipmap.git);
        addcard("Даниил", 1, R.mipmap.pig);
        addcard("Аркадий", 1, R.mipmap.ben);
    }

    void addcard(String name, int type, int img){
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
        if(type == 0){
            tPrice.setText("creator");
        } else {
            tPrice.setText("invited");
        }
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

        params5 = new LinearLayout.LayoutParams(
                75, 75);
        params5.setMargins(10,10,10,10);
        ImageView i2 = new ImageView(this.getContext());
        i2.setLayoutParams(params5);
        i2.setImageResource(R.mipmap.points);




        l = (LinearLayout)getView().findViewById(R.id.linerForCardProfile);

        l4.addView(i2);
        l3.addView(tName);
        l3.addView(tPrice);
        l2.addView(i1);
        l1.addView(l2);
        l1.addView(l3);
        l1.addView(l4);
        c.addView(l1);
        l.addView(c);
    }

}