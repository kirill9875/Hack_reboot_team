package com.example.hack_reboot_team.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.Main2Activity;
import com.example.hack_reboot_team.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    LinearLayout l;
    private Button plus_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        plus_btn = (Button) root.findViewById(R.id.angry_btn3);

        plus_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startQR();

            }
        });


        String user[] = {"Петя","Вася"};
        item w1 = new item("Potato", 32,2, user);
        item w2 = new item("Taxi", 86,1, user);

        item arr[] = {w1,w2};

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int idimg[] = {R.mipmap.ben, R.mipmap.den, R.mipmap.git, R.mipmap.pig};
        int arr[] = {0,0,1,1};
        addcard("Бигмак", "+ $"+"137", arr, idimg);
        arr[0] = 1;
        addcard("Чизбергер", "+ $"+"137", arr, idimg);
        arr[3] = 0;
        addcard("Чикенбургер", "+ $"+"137", arr, idimg);
        arr[2] = 0;
        addcard("Цезарь Ролл", "+ $"+"137", arr, idimg);
        arr[1] = 1;
        addcard("Каротфель Фри", "+ $"+"137", arr, idimg);
    }
    private void startQR() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();


    void addcard(String name, String price, int arr[], int idimg[]){
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
        for(int i = 0; i < idimg.length; i++){
            ImageView i1 = new ImageView(this.getContext());
            i1.setLayoutParams(params5);
            i1.setImageResource(idimg[i]);
            if(!(arr[i] == 1)) {
                i1.setAlpha(100);
            }
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

//        IntentIntegrator.forSupportFragment(this).initiateScan();
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