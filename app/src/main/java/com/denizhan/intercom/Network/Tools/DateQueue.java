package com.denizhan.intercom.Network.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class DateQueue

{

    private SimpleDateFormat dateFormat;

    private Queue<String> ringDateQueue = new LinkedList<>();
    private Queue<String> textDateQueue = new LinkedList<>();
    private Queue<String> audioDateQueue = new LinkedList<>();
    private Queue<String> videoDateQueue = new LinkedList<>();

    public DateQueue(){
        dateFormat = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
    }

    public void addRingDate(){
        ringDateQueue.add(dateFormat.format(Calendar.getInstance().getTime()));
    }

    public void addTextDate(){
        textDateQueue.add(dateFormat.format(Calendar.getInstance().getTime()));
    }

    public void addAudioDate(){
        audioDateQueue.add(dateFormat.format(Calendar.getInstance().getTime()));
    }

    public void addVideoDate(){
        videoDateQueue.add(dateFormat.format(Calendar.getInstance().getTime()));
    }

}