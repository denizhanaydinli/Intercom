package com.denizhan.intercom.Media;
/*
    Yazar: Alp
    Açıklama: Gerçek zamanlı olarak byte formatındaki sesi hoperlörden oynatmak
*/

import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class RealtimeAudioPlayer implements ActivityMediaInteractionInterface {

    private AudioTrack audioTrack; // Gerçek zamanlı ses oynatı class

    public RealtimeAudioPlayer(){
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, AudioTrack.getMinBufferSize(1000, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT), AudioTrack.MODE_STREAM );
        // 8000 hertz de 16 bit çözünürlüğünde tek kanalda kayıt yap, 1000'er 1000'er bufferla sesi oynat
        audioTrack.play();
        // yazmadan önce çağırılması gerekiyor
    }

    @Override
    public void prepare() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if(audioTrack != null){
            audioTrack.stop();
        }
    }

    @Override
    public void destroy() {
        if(audioTrack != null){
            stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

}