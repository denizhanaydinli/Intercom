package com.denizhan.intercom.Network.Tools;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {

    public Queue<String> ringNotificationQueue;
    public Queue<String> textMessageQueue;
    public Queue<String> audioMessageQueue;
    public Queue<String> videoMessageQueue;

    public MessageQueue(){
        ringNotificationQueue = new LinkedList<>();
        textMessageQueue = new LinkedList<>();
        audioMessageQueue = new LinkedList<>();
        videoMessageQueue = new LinkedList<>();
    }

    public void addRingMessage(String message){
        ringNotificationQueue.add(message);
    }

    public void addTextMessage(String message){
        textMessageQueue.add(message);
    }

    public void addAudioMessage(String message){
        audioMessageQueue.add(message);
    }

    public void addVideoMessage(String message){
        videoMessageQueue.add(message);
    }

}