package com.denizhan.intercom.Interaction;

import android.view.View;
import android.widget.Button;
import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.R;
//Denizhan
public class NavigationPanel extends Panel {

    private Button locker_button, text_message_button, audio_message_button, video_message_button;
    private Locker locker;
    private Typewriter typewriter;
    private Mic mic;
    private Camera camera;

    public NavigationPanel(InstanceHolder ih, Locker locker, Typewriter typewriter, Mic mic, Camera camera){
        super(ih, R.id.navigation_layout);
        this.locker = locker;
        this.typewriter = typewriter;
        this.mic = mic;
        this.camera = camera;
        setViews();
        setListeners();
    }

    @Override
    protected void setViews(){
        locker_button = IH.activityInstance.findViewById(R.id.locker_button);
        text_message_button = IH.activityInstance.findViewById(R.id.text_message_button);
        audio_message_button = IH.activityInstance.findViewById(R.id.audio_message_button);
        video_message_button = IH.activityInstance.findViewById(R.id.video_message_button);
    }

    @Override
    protected void setListeners(){
        locker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locker.show();
                typewriter.hide();
                mic.hide();
                camera.hide();
            }
        });

        text_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locker.hide();
                typewriter.show();
                mic.hide();
                camera.hide();
            }
        });

        audio_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locker.hide();
                typewriter.hide();
                mic.show();
                camera.hide();
            }
        });

        video_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locker.hide();
                typewriter.hide();
                mic.hide();
                camera.show();
            }
        });
    }




}
