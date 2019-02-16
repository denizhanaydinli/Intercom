package com.denizhan.intercom.Network;

/*
    Yazacak Olan: Nehir
    Açıklama: Ev sahibine kaydedilen mesajları ve gerçek zamanlı okunan veriyi göndermeye ve karşıdan gönderilen veriyi
    almaya yaracak class.
*/

import android.util.Log;

import com.denizhan.intercom.Network.Tools.COMMANDS;
import com.denizhan.intercom.Network.Tools.MessageQueue;

import java.io.File;
import java.io.FileInputStream;
import java.lang.InterruptedException;
import java.util.Arrays;
import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import java.util.Queue;

public class NetworkConnector {

    private UDPSender udpSender;
    private UDPReceiver udpReceiver;

    private Thread sendingThread, receivingThread;
    private Runnable sendingRunnable, receivingRunnable;
    private boolean sending,  receiving;

    private boolean SEND_REALTIME_DATA = false;
    private boolean SEND_UPDATES = false;
    private MessageQueue messageQueue;

    // baglanilacak ip adresini al

    private NetworkConnector(String ipAdress){
        this.udpSender = new UDPSender(ipAdress);
        this.udpReceiver = new UDPReceiver();
        initialize();
        messageQueue = new MessageQueue();
    }

    public void initialize(){
        // veri gönderme metodunu seç
        sendingRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while(sending){
                        send();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        sendingThread = new Thread(sendingRunnable);
        // veri alma metodunu seç
        receivingRunnable = new Runnable() {
            @Override
            public void run()  {
                try {
                    while(receiving){
                        receive();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

    private void send()throws InterruptedException{
      //buralara gonderme yapilacak
        //data byte ların arraylik eleman olarak gonderilmelerini sagliycak
        if (SEND_REALTIME_DATA) {
            // gerçek zamanlı görüntü ve video gönder
        }

        if(SEND_UPDATES) {
            while(!messageQueue.ringNotificationQueue.isEmpty()) {
                // queue boşalana kadar updateleri gönder
            }
            while(!messageQueue.textMessageQueue.isEmpty()){
                // queue boşalana kadar updateleri gönder
            }
            while(!messageQueue.audioMessageQueue.isEmpty()){
                // queue boşalana kadar updateleri gönder
            }
            while(!messageQueue.videoMessageQueue.isEmpty()){
                // queue boşalana kadar updateleri gönder
            }
            SEND_UPDATES = false;
        }
    }

    private void receive(){
     //alma islemleri yapilacak
        byte [] data = udpReceiver.receive();
    }
    private byte[] fileToByte(File file)
    {
        byte[] file_data = new byte[(int) file.length()];
        //gelen dosyayi bytelara ata..
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(file_data);
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return file_data;
    }




}
