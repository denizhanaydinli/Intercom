package com.denizhan.intercom.Interfaces;

/*
    Yazar: Denizhan
    Açıklama: Bu arayüzün ses kaydedici ve oynatıcı, video kaydedici ve oynatıcı,
    gerçek zamanlı ses ve görüntü classlarını yazanların implement etmesi gerekmektedir.
*/

import android.hardware.Camera;

public interface ActivityMediaInteractionInterface {

    public void prepare();
    public void start();
    public void stop();
    public void destroy();
    //public void onPreviewFrame(byte[] data, Camera camera);

}
