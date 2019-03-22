package com.denizhan.intercom;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.PermissionController;
import com.denizhan.intercom.Interaction.Camera;
import com.denizhan.intercom.Interaction.Locker;
import com.denizhan.intercom.Interaction.Mic;
import com.denizhan.intercom.Interaction.NavigationPanel;
import com.denizhan.intercom.Interaction.Typewriter;
import com.denizhan.intercom.Network.NetworkConnector;
import com.denizhan.intercom.Network.Tools.COMMANDS;

//Denizhan
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private InstanceHolder IH;

    public NetworkConnector networkConnector;

    private NavigationPanel navigationPanel;
    private Locker locker;
    private Typewriter typewriter;
    private Mic mic;
    private Camera camera;

    public PermissionController permissionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.IH = new InstanceHolder( MainActivity.this);
        permissionController = new PermissionController(this.IH);
        initExternalTools();
    }

    private void initExternalTools() {
        networkConnector = new NetworkConnector("192.168.43.1");
        networkConnector.start();

        locker = new Locker("123", this.IH);
        locker.show();

        typewriter = new Typewriter(this.IH, 100);
        typewriter.hide();

        mic = new Mic(this.IH);
        mic.hide();

        camera = new Camera(this.IH);
        camera.hide();

        navigationPanel = new NavigationPanel(this.IH, this.locker, this.typewriter, this.mic, this.camera);
        navigationPanel.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionController.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
