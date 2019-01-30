package com.denizhan.intercom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.Locker;

//Denizhan
public class MainActivity extends AppCompatActivity {

    private InstanceHolder IH;

    private Locker locker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.IH = new InstanceHolder( MainActivity.this);
        initExternalTools();
    }

    private void initExternalTools() {
        locker = new Locker("123", this.IH);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
