package com.denizhan.intercom.Media;

import android.media.MediaPlayer;
import android.widget.ProgressBar;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;

public class CustomAudioPlayer implements ActivityMediaInteractionInterface {

    private MediaPlayer player; // Android'in kendi medya oynatıcısı
    private boolean playing; // Oynuyor mu oynamıyor mu anlamak için boolean
    private int PLAYING_INDEX = 0; // Oynatma indexi
    private String FILE_PATH = "/storage/emulated/0/sample" + PLAYING_INDEX + ".3gp"; // Dosya yolu
    private ProgressBar timebar; // Activity den alınacak progressbar zaman gösterimi için kullanılacak
    private Thread timebarthread; // Progressbarın animasyonu için kullanılacak thread
    private Runnable timebarrunnable; // Thread için kullanılacak method içeriğini tutacak Runnable

    public CustomAudioPlayer()
    {
        player = new MediaPlayer();
    }

    public CustomAudioPlayer(ProgressBar progressbar)
    {
        player = new MediaPlayer();
        setProgressBar(progressbar);
    }

    @Override
    public void prepare()
    {
        try
        {
            player.setDataSource(FILE_PATH); // Oynatılacak dosyayı seç
            player.prepare(); // Oynatma için hazırlan
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void prepare(boolean looping)
    {
        player.setLooping(looping);
        prepare();
    }

    public void prepare(int index) // Direk index alarak dosyayı hazırlama
    {
        setFileIndex(index);
        prepare();
    }

    public void prepare(int index, boolean looping) // Direk index alarak dosyayı hazırlama
    {
        setFileIndex(index);
        player.setLooping(looping);
        prepare();
    }

    @Override
    public void start()
    {
        player.start(); // Oynatmayı başlat
        playing = true;
        if(timebar != null){ // Eğer progressbar verilmiş ise animasyonu başlat
            timebarthread.start();
        }
    }

    @Override
    public void stop()
    {
        player.stop(); // Oynatmayı durdur
        playing = false;
        if(timebar != null) { // Eğer progressbar verilmiş ise animasyonu bitir
            timebarthread = null;
        }
    }

    @Override
    public void destroy()
    {
        player.release(); // MediaPlayer objesini ve kullandığı kaynakları temizle
        playing = false;
        if(timebarthread != null) { // Eğer progressbar verilmiş ise animasyonu bitir
            timebarthread = null;
        }
    }

    public void reset(){
        player.reset();
        playing = false;
        if(timebarthread != null) { // Eğer progressbar verilmiş ise animasyonu bitir
            timebarthread = null;
        }
    }

    public void setFileIndex(int index)
    {
        PLAYING_INDEX = index; // İstenilen indexi belirle
        FILE_PATH = "/storage/emulated/0/sample" + PLAYING_INDEX + ".3gp"; // İstenilen indexteki dosya yolunu al
    }

    public String getPath()
    {
        return FILE_PATH;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public int getPlayingIndex()
    {
        return PLAYING_INDEX;
    }

    private int getAudioDuration() // Ses kaydının toplam zamanını al
    {
        return player.getDuration();
    }

    private int getCurrentTime() // Ses kaydını oynatırken geçerli zamanı al
    {
        return player.getCurrentPosition();
    }

    public void setProgressBar(final ProgressBar progressbar)
    {
        timebar = progressbar; // Uygulama içindeki verilen progressbarı kullan
        timebar.setProgress(0); // Kayıt başlangıcı progressbarı sıfırla
        timebarrunnable = new Runnable()
        {
            @Override
            public void run()
            {
                timebar.setMax(getAudioDuration()); // Progressbar için maximum değeri ayarla
                while(playing)
                {
                    try
                    {
                        timebar.setProgress(getCurrentTime()); // Kayıt sırasında progressbarın değerini güncelle
                        Thread.sleep(50); // 50 milisaniye aralıklarla güncelle.
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                timebar.setProgress(0); // Kayıt bitince progressbarı sıfırla
            }
        };
        timebarthread = new Thread(timebarrunnable); // Kayıt sırasında progressbarı oynatmak için gerekli thread
    }
}