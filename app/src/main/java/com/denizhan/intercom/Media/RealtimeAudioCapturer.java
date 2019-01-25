/*
    Yazar: Alp
    Açıklama: Gerçek zamanlı olarak mikrofondan sesi byte formatında alıp hazır hale getirmek
*/
package com.denizhan.intercom.Media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;

public class RealtimeAudioCapturer implements ActivityMediaInteractionInterface {

    private AudioRecord audioRecord; // ses kaydı için gerekli android classı
    private Thread recordingThread = null;
    private Runnable recordingRunnable = null;
    private boolean recording = false;

    public RealtimeAudioCapturer(){
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 2000);
        // 8000 hertz de 16 bit çözünürlüğünde tek kanalda kayıt yap, buffer boyutu 2000 olsun
        recordingRunnable = new Runnable() {
            @Override
            public void run() {
                while(recording){
                    captureAudio();
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
        // kayda başla
        if(audioRecord != null && !recording){
            audioRecord.startRecording();
            recording = true;
            recordingThread = new Thread(recordingRunnable);
            recordingThread.start();
        }
    }

    @Override
    public void stop() {
        // kaydı durdur
        if(audioRecord != null && recording){
            audioRecord.stop();
            recording = false;
            recordingThread = null;
        }
    }

    @Override
    public void destroy() {
        // kaydı kapa
        stop();
        if(audioRecord != null && !recording) {
            audioRecord.release();
            recording = false;
            recordingThread = null;
        }
    }

    public void captureAudio(){
        byte[] buffer = new byte[2000];
        audioRecord.read(buffer, 0, 2000); // buffer boyutu kadar ses oku
    }
}
