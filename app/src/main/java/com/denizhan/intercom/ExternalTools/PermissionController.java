package com.denizhan.intercom.ExternalTools;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/*
    Yazar: Gizem
    Açıklama: Kullanıcıdan alınacak kamera kullanma, ses kaydetme ve depolama izinlerini kontrol edecek class.
*/

public class PermissionController implements ActivityCompat.OnRequestPermissionsResultCallback {

    private InstanceHolder IH;

    private final int PERMISSIONS_RECORD_AUDIO = 1;

    private OnResultCallback onResultCallback;

    public PermissionController(InstanceHolder ih){
        this.IH = ih;
    }

    public boolean isAudioPermissionGranted(){
        if (ContextCompat.checkSelfPermission(IH.activityInstance, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    public void askForAudioPermission(OnResultCallback callback){
        this.onResultCallback = callback;
        if (ContextCompat.checkSelfPermission(IH.activityInstance, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            this.onResultCallback.onResult(true);
        } else {
            ActivityCompat.requestPermissions(IH.activityInstance, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_RECORD_AUDIO   );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.onResultCallback.onResult(true);
                } else {
                    this.onResultCallback.onResult(false);
                }
            }
        }
    }

    public interface OnResultCallback {
        public void onResult(boolean result);
    }
}