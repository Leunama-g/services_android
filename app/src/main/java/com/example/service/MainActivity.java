package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.service.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding binding;
    boolean state = true;
    String action;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSerice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RingTone.class);
                action = i.getAction();
                if(state){
                    startService(i);
                    binding.btnSerice.setText("Stop Service");
                    state = false;
                }
                else{
                    stopService(i);
                    binding.btnSerice.setText("Start Service");
                    state = true;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i = new Intent(getApplicationContext(), RingTone.class);
        stopService(i);
        binding.btnSerice.setText("Start Service");
        state = true;

    }



}