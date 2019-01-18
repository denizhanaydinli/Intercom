/*
    Yazar: Alp
    Açıklama: Gerçek zamanlı olarak mikrofondan sesi byte formatında alıp hazır hale getirmek
*/
package com.denizhan.intercom.Media;
package com.external.test.Media;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import com.external.test.Interfaces.ActivityMediaInteractionInterface;

public class RealtimeAudioCapturer implements ActivityMediaInteractionInterface {

    private AudioRecord audioRecord; // ses kaydı için gerekli android classı

    public RealtimeAudioCapturer(){
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 2000);
        // 8000 hertz de 16 bit çözünürlüğünde tek kanalda kayıt yap, buffer boyutu 2000
    }

    @Override
    public void prepare() {
        // hazırlayacak bir şey yok
    }

    @Override
    public void start() {
        // kayda başla
        audioRecord.startRecording();
    }

    @Override
    public void stop() {
        // kaydı durdur
        audioRecord.stop();
    }

    @Override
    public void destroy() {
        // kaydı kapa
        audioRecord.release();
    }
}
