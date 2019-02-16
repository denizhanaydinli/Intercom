package com.denizhan.intercom.Media;
import android.media.MediaRecorder;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;
import android.widget.ProgressBar;

public class CustomAudioRecorder implements ActivityMediaInteractionInterface {

    private MediaRecorder recorder; // Android'in kendi medya kaydedicisi
    private boolean recording; // Kaydedip kaydetmediğini anlamak için boolean
    private int RECORDING_INDEX = -1; // Yeni kayıtta dosya yolunu değiştirmek için index
    private String FILE_PATH = "/storage/emulated/0/sample" + RECORDING_INDEX + ".3gp"; // Dosya yolu
    private ProgressBar amplitudebar; // Activity den alınacak progressbar
    private Thread amplitudethread; // Progressbarın animasyonu için kullanılacak thread
    private Runnable amplituderunnable; // Thread için kullanılacak method içeriğini tutacak Runnable

    public CustomAudioRecorder()
    {
        init();
    }

    public CustomAudioRecorder(ProgressBar progressbar)
    {
        init();
        setProgressBar(progressbar);
    }

    private void init(){
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
        if(amplitudebar != null){ // Eğer progressbar verilmiş ise animasyonu başlat
            amplitudethread.start();
        }
    }

    @Override
    public void stop()
    {
        recorder.stop(); // Kaydı durdur
        recording = false;
        if(amplitudebar != null) { // Eğer progressbar verilmiş ise animasyonu bitir
            amplitudethread = null;
        }
    }

    @Override
    public void destroy()
    {
        recorder.release(); // MediaRecorder objesini ve kullandığı kaynakları temizle
        recording = false;
        if(amplitudebar != null) { // Eğer progressbar verilmiş ise animasyonu bitir
            amplitudethread = null;
        }
    }

    public void reset(){
        recorder.reset(); // Bu method çağırılırsa sanki constructor yeni çağırılmış durumuna gelir
        recording = false;
        if(amplitudebar != null) { // Eğer progressbar verilmiş ise animasyonu bitir
            amplitudethread = null;
        }
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

    public String getPath()
    { // Kaydedilen dosya yolunu söyle
        return FILE_PATH;
    }

    public boolean isRecording()
    { // Kayıt durumunu söyle
        return recording;
    }

    public int getRecordingIndex()
    { // Kaçıncı kayıtta olduğunu söyle
        return RECORDING_INDEX;
    }

    public int getAmplitude()
    {
        return recorder.getMaxAmplitude(); // Kayıt sırasında sesin yüksekliğini al
    }

    public void setProgressBar(final ProgressBar progressbar)
    {
        amplitudebar = progressbar; // Uygulama içindeki verilen progressbarı kullan
        amplitudebar.setMax(6500); // Progressbar için maximum değeri ayarla
        amplitudebar.setProgress(0); // Kayıt başlangıcı progressbarı sıfırla
        amplituderunnable = new Runnable()
        {
            @Override
            public void run()
            {
                while(recording)
                {
                    try
                    {
                        amplitudebar.setProgress(recorder.getMaxAmplitude()); // Kayıt sırasında progressbarın değerini güncelle
                        Thread.sleep(50); // 50 milisaniye aralıklarla döngüyü durdur. Eğer çok hızlı olursa animasyon bozuluyor
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                amplitudebar.setProgress(0); // Kayıt bitince progressbarı sıfırla
            }
        };
        amplitudethread = new Thread(amplituderunnable); // Kayıt sırasında progressbarı oynatmak için gerekli thread
    }
}