package com.example.hack_reboot_team.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack_reboot_team.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String user[] = {"Петя","Вася"};
        item w1 = new item("Potato", 32,2, user);
        item w2 = new item("Taxi", 86,1, user);

        item arr[] = {w1,w2};

        return root;
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