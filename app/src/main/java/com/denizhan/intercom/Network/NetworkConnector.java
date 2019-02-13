package com.denizhan.intercom.Network;

/*
    Yazacak Olan: Nehir
    Açıklama: Ev sahibine kaydedilen mesajları ve gerçek zamanlı okunan veriyi göndermeye ve karşıdan gönderilen veriyi
    almaya yaracak class.
*/

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Objects;

public class NetworkConnector {

    private UDPSender udpSender;
    private UDPReceiver udpReceiver;

    private Thread sendingThread, receivingThread;
    private Runnable sendingRunnable, receivingRunnable;
    private boolean sending,  receiving;

    // baglanilacak ip adresini al

    private NetworkConnector(String ipAdress){
        this.udpSender = new UDPSender(ipAdress);
        this.udpReceiver = new UDPReceiver();
        initialize();
    }

    private void initialize(){
        // veri gonderme metodunu sec
        sendingRunnable = new Runnable() {

            @Override
            public void run() {
                while(sending){
                    send();
                }
            }
        };
        sendingThread = new Thread(sendingRunnable);
        // veri alma metodunu sec
        receivingRunnable = new Runnable() {
            @Override
            public void run() {
                while(receiving){
                    receive();
                }
            }
        };
        receivingThread = new Thread(receivingRunnable);
    }

    private void start(){
        //veri gondermeye basla
        sending = true;
        sendingThread.start();

        //veri almaya basla
        receiving = true;
        receivingThread.start();
    }

    private void stop(){
        sending = false;
        receiving = false;
    }

    private void destroy(){
        sending = false;
        sendingThread = null;

        receiving = false;
        receivingThread = null;
    }

    private void send(){
      //buralara gonderme yapilacak
        //data byte ların arraylik eleman olarak gonderilmelerini sagliycak
        udpSender.send(new byte[0]);
    }

    private void receive(){
     //alma islemleri yapilacak
        byte [] data = udpReceiver.receive();
    }

    public enum COMMANDS
    {

            SEND_AUDIO("send_audio"),
            END_AUDIO("end_audio"),

            CHECK_UPDATES("check_updates"),

            SEND_VIDEO("send_video"),
            END_VIDEO("end_video"),

            OPEN_LOCK("open_lock");

            REAL_TIME_BEGIN("real_time_begin"),
            REAL_TIME_END("real_time_end"),

            RING("ring_begin"),

            TEXT("text_begin"),
            AUDIO("audio_begin"),
            VIDEO_BEGIN("video_begin"),
            VIDEO_END("video_end");

            private final String type;
            COMMANDS(String str) {
                type = str;
            }


            public String toString() {
                return this.type;
            }
        }



}
