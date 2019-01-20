package com.denizhan.intercom.Media;
//deneme
import android.media.MediaPlayer;
import com.denizhan.intercom.Interfaces.ActivityMediaInteractionInterface;

import java.io.IOException;

public class CustomAudioPlayer implements ActivityMediaInteractionInterface {

    private MediaPlayer player; // Android'in kendi medya oynatıcısı
    public boolean playing;

    public CustomAudioPlayer()
    {
        player = new MediaPlayer();
    }

    @Override
    public void prepare()
    {
        try
        {
            player.setDataSource("/storage/emulated/0/sample.3gp"); // Oynatılacak dosyayı seç
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
}