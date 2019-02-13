package com.denizhan.intercom.Media;

import android.media.MediaPlayer;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;
import java.io.IOException;

public class CustomAudioPlayer implements ActivityMediaInteractionInterface {

    private MediaPlayer player; // Android'in kendi medya oynatıcısı
    public boolean playing; // Oynuyor mu oynamıyor mu anlamak için boolean
    private String FILE_PATH = "/storage/emulated/0/sample0.3gp"; // Dosya yolu

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

    public void setFilePath(String path)
    {
        FILE_PATH = path; // Dosya yolunu değiştir
    }
}