package com.denizhan.intercom.Media;

/*
    Yazacak Olan: Buğra
    Açıklama: 3gp formatında video kaydedici
*/
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceView;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;


public class CustomVideoRecorder implements ActivityMediaInteractionInterface, Camera.PreviewCallback{

    private MediaRecorder media_recorder; // video kaydedicisi
    private Camera camera; // video kamerası
    private SurfaceView surface_view; // video kaydedilirken gösterileceği zemin
    private boolean previewing = false;
    private boolean recording = false;
    private int index = -1;
    private String path = "/storage/emulated/0/video" + index + ".mp4"; // videonun dosya ismi

    public CustomVideoRecorder(SurfaceView surfaceview){
        this.media_recorder = new MediaRecorder();
        this.surface_view = surfaceview;
    }

    @Override
    public void prepare() {
        stopPreview(); // eğer preview yapıyorsa durdur
        this.camera = Camera.open(1); // ön kamerayı aç
        this.camera.unlock(); // kamerayı kullanıma aç
        this.media_recorder.setCamera(this.camera); // video kaydedicinin kamera kaynağını belirle
        this.media_recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER); // ses kaynağını belirle
        this.media_recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // görüntü kaynağını belirle
        this.media_recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW)); // düşük görüntü kamerasını seç
        nextRecord();
        this.media_recorder.setOutputFile(path); // dosya yolunu belirle
        this.media_recorder.setPreviewDisplay(this.surface_view.getHolder().getSurface()); // kayıt sırasındaki önizleme ekranını belirle
        try {
            media_recorder.prepare(); // kayda hazırlan
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        if(this.media_recorder != null){
            this.media_recorder.start(); // kayda başla
            this.recording = true;
        }
    }

    @Override
    public void stop() {
        if(this.media_recorder != null){
            this.media_recorder.stop(); // kaydı bitir
            this.recording = false;
        }
        if(this.camera != null){
            this.camera.lock(); // kamerayı kullanıma kapat
            this.camera.release(); // kamerayı sil
        }
    }

    @Override
    public void destroy() {
        if(this.media_recorder != null){
            this.media_recorder.release(); // kaydediciyi sil
            this.recording = false;
        }
        if(this.camera != null){ // kamerayı sil
            this.camera.lock();
            this.camera.release();
            this.previewing = false;
        }
    }

    public void startPreview(){
        try {
            if(isRecording()){ // eğer kayıttaysa durdur
                stop();
            }
            this.camera = Camera.open(1); // ön kamerayı aç
            this.camera.setPreviewDisplay(this.surface_view.getHolder()); // oynatma yüzeyini belirle
            this.camera.setPreviewCallback(this);
            this.camera.startPreview(); // previewi başlat
            this.previewing = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPreview(){
        if(isPreviewing()){
            this.camera.stopPreview(); // previewi durdur
            this.camera.release(); // kamerayı kapat
            this.previewing = false;
        }
    }

    public boolean isPreviewing(){
        return this.previewing; // preview yapıyor mu yapmıyor mu anlamak için boolean döndür
    }

    public boolean isRecording(){
        return this.recording; // kayıt yapıyor mu yapmıyor mu anlamak için boolean döndür
    }

    public void setPath(String path){
        this.path = path;
    }

    public void nextRecord(){
        this.index++;
        setPath("/storage/emulated/0/video" + this.index + ".mp4");
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}