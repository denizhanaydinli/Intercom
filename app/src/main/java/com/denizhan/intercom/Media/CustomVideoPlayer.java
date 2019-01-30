package com.denizhan.intercom.Media;

/*
    Yazacak Olan: Buğra
    Açıklama: 3gp formatında video oynatıcı eklendi
*/
import android.media.MediaPlayer;
import android.view.SurfaceView;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;

public class CustomVideoPlayer implements ActivityMediaInteractionInterface {
    private MediaPlayer media_player;
    private SurfaceView surface_view;
    public String path = "/storage/emulated/0/video.mp4"; // videonun dosya ismi

    public CustomVideoPlayer(){
        this.media_player = new MediaPlayer();
        this.surface_view = surface_view;

    }
    /* yorum*/
    @Override
    public void prepare() {
        try {
            this.media_player.setDisplay(this.surface_view.getHolder()); // videonun oynatılacağı surface
            this.media_player.setDataSource(path);
            this.media_player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {
        if(this.media_player != null){
            this.media_player.start();
        }

    }

    @Override
    public void stop() {
        if(this.media_player != null){
            this.media_player.stop();
        }

    }

    @Override
    public void destroy() {
        stop();
        if(this.media_player != null){
            this.media_player.release();
        }

    }
}
