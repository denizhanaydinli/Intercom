package com.denizhan.intercom.ExternalTools;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/*
    Yazar: Gizem
    Açıklama: Kullanıcıdan alınacak kamera kullanma, ses kaydetme ve depolama izinlerini kontrol edecek class.
*/

public class PermissionController {

    private InstanceHolder IH;

    private final int PERMISSIONS_RECORD_AUDIO = 1;
    private final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 2;
    private final int PERMISSIONS_CAMERA = 3;

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
        if (isAudioPermissionGranted()) {
            this.onResultCallback.onResult(true);
        } else {
            ActivityCompat.requestPermissions(IH.activityInstance, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_RECORD_AUDIO);
        }
    }

    public boolean isStoragePermissionGranted(){
        if (ContextCompat.checkSelfPermission(IH.activityInstance, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    public void askForStoragePermission(OnResultCallback callback){
        this.onResultCallback = callback;
        if (isStoragePermissionGranted()) {
            this.onResultCallback.onResult(true);
        } else {
            ActivityCompat.requestPermissions(IH.activityInstance, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }
    }

    public boolean isVideoPermissionGranted(){
        if (ContextCompat.checkSelfPermission(IH.activityInstance, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    public void askForVideoPermission(OnResultCallback callback){
        this.onResultCallback = callback;
        if (isVideoPermissionGranted()) {
            this.onResultCallback.onResult(true);
        } else {
            ActivityCompat.requestPermissions(IH.activityInstance, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_CAMERA);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.onResultCallback.onResult(true);
                } else {
                    this.onResultCallback.onResult(false);
                }
            }

            case PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.onResultCallback.onResult(true);
                } else {
                    this.onResultCallback.onResult(false);
                }
            }

            case PERMISSIONS_CAMERA: {
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