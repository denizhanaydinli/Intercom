package com.denizhan.intercom.Interaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.Media.CustomAudioPlayer;
import com.denizhan.intercom.Media.CustomAudioRecorder;
import com.denizhan.intercom.R;

public class Mic extends Panel {

    private ProgressBar audio_amplitude_progress_bar, audio_remaining_time_bar;
    private Button record_audio_button, send_audio_button, cancel_audio_button;

    private CustomAudioRecorder customAudioRecorder;
    private CustomAudioPlayer customAudioPlayer;

    private boolean toolSet = false;

    public Mic(InstanceHolder ih){
        super(ih, R.id.audio_layout);
        setTools();
    }

    @Override
    protected void setViews() {
        audio_amplitude_progress_bar = IH.activityInstance.findViewById(R.id.audio_amplitude_progress_bar);
        audio_remaining_time_bar = IH.activityInstance.findViewById(R.id.audio_remaining_time_bar);
        record_audio_button = IH.activityInstance.findViewById(R.id.record_audio_button);
        send_audio_button = IH.activityInstance.findViewById(R.id.send_audio_button);
        send_audio_button.setVisibility(View.INVISIBLE);
        cancel_audio_button = IH.activityInstance.findViewById(R.id.cancel_audio_button);
        cancel_audio_button.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setListeners() {
        record_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTools();
                Log.e("***", "record_audio_button" + " :onclick");
            }
        });

        send_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("***", "send_audio_button" + " :onclick");
            }
        });

        cancel_audio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("***", "cancel_audio_button" + " :onclick");
            }
        });
    }

    private void setTools(){
    }

    private void initAudio(){

    }
}
