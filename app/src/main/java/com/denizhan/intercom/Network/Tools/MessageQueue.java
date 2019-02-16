package com.denizhan.intercom.Network.Tools;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {

    private DateQueue dateQueue;

    public Queue<String> ringNotificationQueue = new LinkedList<>();
    public Queue<String> textMessageQueue = new LinkedList<>();
    public Queue<String> audioMessageQueue = new LinkedList<>();
    public Queue<String> videoMessageQueue = new LinkedList<>();

    public MessageQueue(){
        this.dateQueue = new DateQueue();
    }

    public void addRingMessage(String message){
        ringNotificationQueue.add(message);
        dateQueue.addRingDate();
    }

    public void addTextMessage(String message){
        textMessageQueue.add(message);
        dateQueue.addTextDate();
    }

    public void addAudioMessage(String message){
        audioMessageQueue.add(message);
        dateQueue.addAudioDate();
    }

    public void addVideoMessage(String message){
        videoMessageQueue.add(message);
        dateQueue.addVideoDate();
    }

}