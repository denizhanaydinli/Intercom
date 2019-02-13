package com.denizhan.intercom.Interaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.PermissionController;
import com.denizhan.intercom.Media.CustomAudioPlayer;
import com.denizhan.intercom.Media.CustomAudioRecorder;
import com.denizhan.intercom.R;

public class Mic extends Panel {

    private ProgressBar audio_amplitude_progress_bar, audio_remaining_time_bar;
    private Button record_audio_button, send_audio_button, stop_audio_button, delete_audio_button;

    private CustomAudioRecorder customAudioRecorder;
    private CustomAudioPlayer customAudioPlayer;

    private boolean toolSet = false;

    public Mic(InstanceHolder ih){
        super(ih, R.id.audio_layout);
    }

    @Override
    protected void setViews() {
        audio_amplitude_progress_bar = IH.activityInstance.findViewById(R.id.audio_amplitude_progress_bar);
        audio_amplitude_progress_bar.setVisibility(View.INVISIBLE);
        audio_remaining_time_bar = IH.activityInstance.findViewById(R.id.audio_remaining_time_bar);
        audio_remaining_time_bar.setVisibility(View.INVISIBLE);
        record_audio_button = IH.activityInstance.findViewById(R.id.record_audio_button);
        send_audio_button = IH.activityInstance.findViewById(R.id.send_audio_button);
        send_audio_button.setVisibility(View.INVISIBLE);
        stop_audio_button = IH.activityInstance.findViewById(R.id.stop_audio_button);
        stop_audio_button.setVisibility(View.INVISIBLE);
        delete_audio_button = IH.activityInstance.findViewById(R.id.delete_audio_button);
        delete_audio_button.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setListeners() {
        record_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IH.activityInstance.permissionController.isAudioPermissionGranted() && IH.activityInstance.permissionController.isStoragePermissionGranted()){
                    setTools();
                    customAudioRecorder.prepare();
                    customAudioRecorder.start();
                    audio_amplitude_progress_bar.setVisibility(View.VISIBLE);
                    stop_audio_button.setVisibility(View.VISIBLE);
                    record_audio_button.setVisibility(View.INVISIBLE);
                }else{
                    passAudioPermission();
                }

            }
        });

        send_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customAudioPlayer.playing){
                    customAudioPlayer.stop();
                }
                audio_remaining_time_bar.setVisibility(View.INVISIBLE);
                record_audio_button.setVisibility(View.VISIBLE);
                delete_audio_button.setVisibility(View.INVISIBLE);
                send_audio_button.setVisibility(View.INVISIBLE);
            }
        });

        stop_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customAudioRecorder.recording){
                    customAudioRecorder.stop();
                    customAudioRecorder.destroy();
                    toolSet = false;
                    audio_amplitude_progress_bar.setVisibility(View.INVISIBLE);
                    audio_remaining_time_bar.setVisibility(View.VISIBLE);
                    send_audio_button.setVisibility(View.VISIBLE);
                    delete_audio_button.setVisibility(View.VISIBLE);
                    stop_audio_button.setVisibility(View.INVISIBLE);

                    customAudioPlayer.prepare();
                    customAudioPlayer.start();
                }
            }
        });

        delete_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customAudioPlayer.playing){
                    customAudioPlayer.stop();
                }
                audio_remaining_time_bar.setVisibility(View.INVISIBLE);
                record_audio_button.setVisibility(View.VISIBLE);
                delete_audio_button.setVisibility(View.INVISIBLE);
                send_audio_button.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void passAudioPermission(){
        if(IH.activityInstance.permissionController.isAudioPermissionGranted()){
            passExternalPermission();
        }else{
            IH.activityInstance.permissionController.askForAudioPermission(new PermissionController.OnResultCallback() {
                @Override
                public void onResult(boolean result) {
                    passExternalPermission();
                }
            });
        }
    }

    private void passExternalPermission(){
        if(IH.activityInstance.permissionController.isStoragePermissionGranted()) {
            setTools();
        }else{
            IH.activityInstance.permissionController.askForStoragePermission(new PermissionController.OnResultCallback() {
                @Override
                public void onResult(boolean result) {
                    if(result) {
                        setTools();
                    } else {

                    }
                }
            });
        }
    }

    private void setTools(){
        if(toolSet){
            return;
        }
        customAudioRecorder = new CustomAudioRecorder();
        customAudioPlayer = new CustomAudioPlayer();
        toolSet = true;
    }
}
