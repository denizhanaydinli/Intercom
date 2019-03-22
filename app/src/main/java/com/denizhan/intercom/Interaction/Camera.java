package com.denizhan.intercom.Interaction;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.PermissionController;
import com.denizhan.intercom.Media.CustomVideoPlayer;
import com.denizhan.intercom.Media.CustomVideoRecorder;
import com.denizhan.intercom.R;

public class Camera extends Panel {

    private Button record_video_button, delete_video_button, send_video_button, stop_video_button;

    private SurfaceView surfaceView;

    private CustomVideoRecorder customVideoRecorder;
    private CustomVideoPlayer customVideoPlayer;

    private boolean toolSet = false;

    public Camera(InstanceHolder ih){
        super(ih, R.id.video_layout);
    }

    @Override
    protected void setViews() {
        surfaceView = IH.activityInstance.findViewById(R.id.video_surface);
        record_video_button = IH.activityInstance.findViewById(R.id.record_video_button);
        delete_video_button = IH.activityInstance.findViewById(R.id.delete_video_button);
        delete_video_button.setVisibility(View.INVISIBLE);
        send_video_button = IH.activityInstance.findViewById(R.id.send_video_button);
        send_video_button.setVisibility(View.INVISIBLE);
        stop_video_button = IH.activityInstance.findViewById(R.id.stop_video_button);
        stop_video_button.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setListeners() {
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(toolSet && !customVideoRecorder.isPreviewing()){
                    customVideoRecorder.startPreview();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        record_video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IH.activityInstance.permissionController.isVideoPermissionGranted() && IH.activityInstance.permissionController.isStoragePermissionGranted()){
                    setTools();
                    if(!customVideoRecorder.isRecording()){
                        customVideoRecorder.prepare();
                        customVideoRecorder.start();
                    }
                    record_video_button.setVisibility(View.INVISIBLE);
                    stop_video_button.setVisibility(View.VISIBLE);
                }else{
                    passVideoPermission();
                }

            }
        });

        delete_video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customVideoPlayer.stop();
                customVideoPlayer.destroy();
                customVideoRecorder.startPreview();

                delete_video_button.setVisibility(View.INVISIBLE);
                send_video_button.setVisibility(View.INVISIBLE);
                record_video_button.setVisibility(View.VISIBLE);
            }
        });

        send_video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customVideoPlayer.stop();
                customVideoPlayer.destroy();
                customVideoRecorder.startPreview();

                delete_video_button.setVisibility(View.INVISIBLE);
                send_video_button.setVisibility(View.INVISIBLE);
                record_video_button.setVisibility(View.VISIBLE);

                String path = "/storage/emulated/0/video" + "0" + ".mp4";
                IH.activityInstance.networkConnector.messageQueue.addVideoMessage(path);

            }
        });

        stop_video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customVideoRecorder.isRecording()){
                    customVideoRecorder.stop();
                    customVideoPlayer = new CustomVideoPlayer(surfaceView);
                    customVideoPlayer.prepare( "/storage/emulated/0/video" + "0" + ".mp4");
                    customVideoPlayer.start();
                }
                stop_video_button.setVisibility(View.INVISIBLE);
                delete_video_button.setVisibility(View.VISIBLE);
                send_video_button.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        //requestPreview = true;
        passVideoPermission();
    }

    @Override
    public void hide() {
        super.hide();
        if(customVideoRecorder != null && customVideoRecorder.isPreviewing()){
            customVideoRecorder.stopPreview();
        }
    }

    private void passVideoPermission(){
        if(IH.activityInstance.permissionController.isVideoPermissionGranted()){
            passExternalPermission();
        }else{
            IH.activityInstance.permissionController.askForVideoPermission(new PermissionController.OnResultCallback() {
                @Override
                public void onResult(boolean result) {
                    requestPreview = true;
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

    boolean requestPreview = false;
    private void setTools(){
        if(toolSet){
            return;
        }
        customVideoRecorder = new CustomVideoRecorder(surfaceView);
        customVideoPlayer = new CustomVideoPlayer(surfaceView);
        if(requestPreview){
            customVideoRecorder.startPreview();
            requestPreview = false;
        }
        toolSet = true;
    }
}
