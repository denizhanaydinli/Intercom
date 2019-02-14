package com.denizhan.intercom.Media;
/*
    Yazar: Alp
    Açıklama: Gerçek zamanlı olarak byte formatındaki sesi hoperlörden oynatmak
*/

import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;


public class RealtimeAudioPlayer implements ActivityMediaInteractionInterface {

    private AudioTrack audioTrack; // Gerçek zamanlı ses oynatma classı
    private boolean playing = false;
    private Thread playingThread = null;
    private Runnable playingRunnable = null;
    private int sampleIndex = 0; // nerde kaldığını hatırlamak için sample indexi
    private ByteArrayOutputStream outputStream = null;

    public RealtimeAudioPlayer(){
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT), AudioTrack.MODE_STREAM );
        // 8000 hertz de 16 bit çözünürlüğünde tek kanalda kayıt yap, 1000'er 1000'er bufferla sesi oynat
        outputStream = new ByteArrayOutputStream();
        audioTrack.play();
        // yazmadan önce çağırılması gerek
        playingRunnable = new Runnable() {
            @Override
            public void run() {
                while(playing){
                    try {
                        write();
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public void prepare() {
        // hazırlayacak bir şey yok
    }

    @Override
    public void start() {
        if(!playing){
            playing = true;
            playingThread = new Thread(playingRunnable);
            playingThread.start();
        }
    }

    @Override
    public void stop() {
        if(audioTrack != null && playing){
            audioTrack.stop();
            playing = false;
            playingThread = null;
            outputStream.reset(); // bufferı sıfırla
            sampleIndex = 0;
        }
    }

    @Override
    public void destroy() {
        if(audioTrack != null){
            stop();
            audioTrack.release();
            audioTrack = null;
            outputStream.reset();
            outputStream = null;
        }
    }

    public void pushAudio(byte[] data){
        // oynatmak için yeni ses ekle
        try {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(){
        byte[] nextByte = getAudioBytes();
        if(nextByte != null){
            // eğer boş değilse yazdırr
            audioTrack.write(nextByte, 0, nextByte.length); // hoperlörden ses çıkışı yapar
        }
    }

    private byte[] getAudioBytes(){
        int streamSize = outputStream.size();
        if(streamSize > 0){
            if(streamSize > sampleIndex + 1000){
                byte[] data = Arrays.copyOfRange(outputStream.toByteArray(), sampleIndex, sampleIndex + 1000 - 1);
                sampleIndex += 1000;
                return data;
            }else{
                return null;
            }
        } else {
            return null;
        }
    }
}