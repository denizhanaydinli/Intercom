package com.denizhan.intercom.Media;

import android.media.MediaPlayer;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;


public class CustomAudioPlayer implements ActivityMediaInteractionInterface {

    private MediaPlayer player; // Android'in kendi medya oynatıcısı
    private boolean playing; // Oynuyor mu oynamıyor mu anlamak için boolean
    private int PLAYING_INDEX = 0; // Oynatma indexi
    private String FILE_PATH = "/storage/emulated/0/sample" + PLAYING_INDEX + ".3gp"; // Dosya yolu

    public CustomAudioPlayer()
    {
        player = new MediaPlayer();
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

    public void prepare(int index) // Direk index alarak dosyayı hazırlama
    {
        setFileIndex(index);
        prepare();
    }

    @Override
    public void start()
    {
        player.start(); // Oynatmayı başlat
        playing = true;
    }

    @Override
    public void stop()
    {
        player.stop(); // Oynatmayı durdur
        playing = false;
    }

    @Override
    public void destroy()
    {
        player.release(); // MediaPlayer objesini ve kullandığı kaynakları temizle
        playing = false;
    }

    public void reset(){
        player.reset();
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
}