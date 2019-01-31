package com.denizhan.intercom.Media;
import android.media.MediaRecorder;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;

public class CustomAudioRecorder implements ActivityMediaInteractionInterface {

    private MediaRecorder recorder; // Android'in kendi medya kaydedicisi
    public boolean recording; // Kaydedip kaydetmediğini anlamak için boolean
    private int RECORDING_INDEX = -1; // Yeni kayıtta dosya yolunu değiştirmek için index
    private String FILE_PATH = "/storage/emulated/0/sample" + RECORDING_INDEX + ".3gp"; // Dosya yolu

    public CustomAudioRecorder()
    {
        nextRecord(); // Her kayıtta dosya yolunu güncelle
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
        recorder.setOutputFile(FILE_PATH); // Dosyanın kaydedileceği yeri seç
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
        recording = true;
    }

    @Override
    public void stop()
    {
        recorder.stop(); // Kaydı durdur
        recording = false;
    }

    @Override
    public void destroy()
    {
        recorder.release(); // MediaRecorder objesini ve kullandığı kaynakları temizle
        recording = false;
    }

    public void setFilePath(String path)
    {
        FILE_PATH = path; // Dosya yolunu değiştir
    }

    public void nextRecord()
    {
        RECORDING_INDEX = RECORDING_INDEX + 1; // Bir sonraki kayda geç
        setFilePath("/storage/emulated/0/sample" + RECORDING_INDEX + ".3gp"); // Dosya yolunu değiştir
    }
}