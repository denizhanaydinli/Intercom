package com.denizhan.intercom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.PermissionController;
import com.denizhan.intercom.Interaction.Camera;
import com.denizhan.intercom.Interaction.Locker;
import com.denizhan.intercom.Interaction.Mic;
import com.denizhan.intercom.Interaction.NavigationPanel;
import com.denizhan.intercom.Interaction.Typewriter;

//Denizhan
public class MainActivity extends AppCompatActivity {

    private InstanceHolder IH;

    private NavigationPanel navigationPanel;
    private Locker locker;
    private Typewriter typewriter;
    private Mic mic;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.IH = new InstanceHolder( MainActivity.this);
        initExternalTools();
    }

    private void initExternalTools() {
        locker = new Locker("123", this.IH);
        locker.show();

        typewriter = new Typewriter(this.IH);
        typewriter.hide();

        mic = new Mic(this.IH);
        mic.hide();

        camera = new Camera(this.IH);
        camera.hide();

        navigationPanel = new NavigationPanel(this.IH, this.locker, this.typewriter, this.mic, this.camera);
        navigationPanel.show();
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
