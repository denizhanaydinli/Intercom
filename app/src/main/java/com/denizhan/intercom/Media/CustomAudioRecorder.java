package com.denizhan.intercom.Media;
import android.media.MediaRecorder;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;

public class CustomAudioRecorder implements ActivityMediaInteractionInterface {

    private MediaRecorder recorder; // Android'in kendi medya kaydedicisi

    public CustomAudioRecorder()
    {
        recorder = new MediaRecorder(); // MediaRecorder objesini oluştur
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // Medya girişi olarak mikrofonu seç
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // Medya formatı olarak 3gp formatını kullan
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // AMR_NB (NarrowBand) ses kaydı kodeği, 8 bin Hz'de örnekleme alıyor.
        // (Wideband 16 bin Hz'de örnekleme alıyor).
        // Dosya boyutunu küçük tutmak için narrowband kullandım.
    }

    @Override
    public void prepare()
    {
        recorder.setOutputFile("/storage/emulated/0/sample.3gp"); // Dosyanın kaydedileceği yeri seç
        try
        {
            recorder.prepare(); // Kayıt için hazırlan
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void start()
    {
        recorder.start(); // Kayda başla
    }

    @Override
    public void stop()
    {
        recorder.stop(); // Kaydı durdur
    }

    @Override
    public void destroy()
    {
        recorder.release(); // MediaRecorder objesini ve kullandığı kaynakları temizle
    }
}