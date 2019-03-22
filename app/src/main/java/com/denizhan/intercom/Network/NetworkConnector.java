package com.denizhan.intercom.Network;
/*
    Yazacak Olan: Nehir
    AÃ§Ä±klama: Ev sahibine kaydedilen mesajlarÄ± ve gerÃ§ek zamanlÄ± okunan veriyi gÃ¶ndermeye ve karÅŸÄ±dan gÃ¶nderilen veriyi
    almaya yaracak class.
*/

import android.util.Log;

import com.denizhan.intercom.Network.Tools.ByteSplitter;
import com.denizhan.intercom.Network.Tools.COMMANDS;
import com.denizhan.intercom.Network.Tools.MessageQueue;
import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

public class NetworkConnector {

    private UDPSender udpSender;
    private UDPReceiver udpReceiver;

    private Thread sendingThread, receivingThread;
    private Runnable sendingRunnable, receivingRunnable;
    private boolean sending,  receiving;

    private byte[] send_data;

    public MessageQueue messageQueue;

    public NetworkConnector(String ipAdress){
        this.udpSender = new UDPSender(ipAdress);
        this.udpReceiver = new UDPReceiver();
        initialize();
    }

    public void initialize(){
        sendingRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while(sending){
                        send();
                        Thread.sleep(75);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        sendingThread = new Thread(sendingRunnable);

        receivingRunnable = new Runnable() {
            @Override
            public void run()  {
                try {
                    while(receiving){
                        receive();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        receivingThread = new Thread(receivingRunnable);

        send_data = new byte[65000];

        messageQueue = new MessageQueue();
    }

    public void start(){
        sending = true;
        sendingThread.start();

        receiving = true;
        receivingThread.start();
    }

    public void stop(){
        sending = false;
        receiving = false;
    }

    private void destroy(){
        sending = false;
        sendingThread = null;

        receiving = false;
        receivingThread = null;
    }

    private void send() throws InterruptedException {
        //if(send realtime data)
        //setVideoData(CustomVideoRecorder.CAMERA_DATA);
        //udpSender.send(send_data);

        //if(send update)
        if(!messageQueue.textMessageQueue.isEmpty()){
            udpSender.send(COMMANDS.TEXT_BEGIN.getBytes());
            Log.e("***", new String("tb") + "");
            Thread.sleep(100);
            while(!messageQueue.textMessageQueue.isEmpty()){
                udpSender.send(Objects.requireNonNull(messageQueue.textMessageQueue.poll()).getBytes());
                Log.e("***", new String("m") + "");

                Thread.sleep(100);
            }
            udpSender.send(COMMANDS.TEXT_END.getBytes());
            Log.e("***", new String("te") + "");

            Thread.sleep(100);
        } else if(!messageQueue.audioMessageQueue.isEmpty()){
            udpSender.send(COMMANDS.AUDIO_BEGIN.getBytes());
            Log.e("***", new String("AUDIO_BEGIN") + "");
            Thread.sleep(100);
            while(!messageQueue.audioMessageQueue.isEmpty()){
                String path = Objects.requireNonNull(messageQueue.audioMessageQueue.poll());

                File file = new File(path);

                byte[] file_data = new byte[(int) file.length()];

                try {
                    FileInputStream fis = new FileInputStream(file);
                    if(fis.read(file_data)==-1){
                        Log.e("***", "Audio data is placed.");
                    }
                    fis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                udpSender.send(file_data);
                Log.e("***", file_data.length + " bytes sent");
                Thread.sleep(100);
            }
            udpSender.send(COMMANDS.AUDIO_END.getBytes());
            Log.e("***", new String("AUDIO_END") + "");

            Thread.sleep(100);
        } else if(!messageQueue.videoMessageQueue.isEmpty()){
            udpSender.send(COMMANDS.VIDEO_BEGIN.getBytes());
            Log.e("***", new String("VIDEO_BEGIN") + "");
            Thread.sleep(100);
            while(!messageQueue.videoMessageQueue.isEmpty()){
                String path = Objects.requireNonNull(messageQueue.videoMessageQueue.poll());
                File file = new File(path);

                byte[] file_data = new byte[(int) file.length()];

                try {
                    FileInputStream fis = new FileInputStream(file);
                    if(fis.read(file_data)==-1){
                        Log.e("***", "Video data is placed.");
                    }
                    fis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Log.e("***", new String("VIDEO_TOLTAL SIZE") + " " + file_data.length);

                ByteSplitter bs = new ByteSplitter(file_data, 65500);
                byte[] data_to_send = bs.nextData();

                while (data_to_send != null){
                    Log.e("***", new String("VIDEO_PART_SEND") + " " + data_to_send.length);
                    udpSender.send(data_to_send);
                    data_to_send = bs.nextData();
                    Thread.sleep(100);
                }
            }
            udpSender.send(COMMANDS.VIDEO_END.getBytes());
            Log.e("***", new String("VIDEO_END") + "");

            Thread.sleep(100);
        }


    }

    private void receive(){
        byte [] data = udpReceiver.receive();
        Log.e("***", "Intercom: " + new String(data));
    }

    public void setVideoData(byte[] camera_data){
        //videoDataPlaced = false;

        Log.e("***", "CData " + camera_data.length + "");

        System.arraycopy(camera_data, 0, send_data, 2, camera_data.length);
        send_data[0] = (byte) (camera_data.length);
        send_data[1] = (byte) (camera_data.length >> 8);

        //videoDataPlaced = true;
    }

    /*public void setAudioData(byte[] data){
        System.arraycopy(data, 0, real_time_data, real_time_data.length-data.length, data.length);
    }*/

}


/*
public class NetworkConnector {

    private InetAddress host_address;

    private DatagramSocket receivingSocket, sendingSocket;
    private DatagramPacket receivingPacket;

    private static final int PACKET_SIZE = 65500;
    public byte[] receiving_bytes = new byte[PACKET_SIZE];

    private String NETWORK_COMMAND = "";

    private byte[] audio_data;
    private boolean audioIsReady = false;
    public boolean pushAudio = true;

    private Thread sendingThread, receivingThread;
    private boolean sending = true,  receiving = true;

    private MainActivity.InstanceHolder IH;

    public void setCommand(String command){
        this.NETWORK_COMMAND = command;
    }
    public void setAudioData(byte[] data){
        audioIsReady = false;
        audio_data = data;
        audioIsReady = true;
    }

    private void send(){
        DatagramPacket sendingPacket;
        try {
            switch (NETWORK_COMMAND) {
                case "check_updates": {
                    byte[] data = NETWORK_COMMAND.getBytes();
                    sendingPacket = new DatagramPacket(data, data.length, host_address, 4445);
                    sendingSocket.send(sendingPacket);
                    Thread.sleep(100);
                    break;
                }
                case "send_audio": {
                    byte[] data = NETWORK_COMMAND.getBytes();
                    sendingPacket = new DatagramPacket(data, data.length, host_address, 4445);
                    sendingSocket.send(sendingPacket);
                    Thread.sleep(100);
                    while (!NETWORK_COMMAND.equals("end_audio")) {
                        if (audioIsReady) {
                            sendingPacket = new DatagramPacket(audio_data, audio_data.length, host_address, 4445);
                            sendingSocket.send(sendingPacket);
                            audioIsReady = false;
                        }
                    }
                    Thread.sleep(100);
                    break;
                }
                case "end_audio": {
                    byte[] data = NETWORK_COMMAND.getBytes();
                    sendingPacket = new DatagramPacket(data, data.length, host_address, 4445);
                    sendingSocket.send(sendingPacket);
                    Thread.sleep(100);
                    break;
                }
                case "send_video": {
                    byte[] data = NETWORK_COMMAND.getBytes();
                    sendingPacket = new DatagramPacket(data, data.length, host_address, 4445);
                    sendingSocket.send(sendingPacket);
                    Thread.sleep(100);
                    break;
                }
                case "end_video": {
                    byte[] data = NETWORK_COMMAND.getBytes();
                    sendingPacket = new DatagramPacket(data, data.length, host_address, 4445);
                    sendingSocket.send(sendingPacket);
                    Thread.sleep(100);
                    break;
                }
                case "open_lock": {
                    byte[] data = NETWORK_COMMAND.getBytes();
                    sendingPacket = new DatagramPacket(data, data.length, host_address, 4445);
                    sendingSocket.send(sendingPacket);
                    Thread.sleep(100);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void receive() {
        try {
            receivingSocket.receive(receivingPacket);
            String tip = new String(Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength()));
            Thread.sleep(10);
            switch (tip) {
                case "real_time_begin": {
                    while (true) {
                        receivingSocket.receive(receivingPacket);
                        byte[] piece = Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength());
                        if (new String(piece).equals("real_time_end")) {
                            break;
                        } else {
                            pushAudio = true;
                        }
                    }
                    break;
                }
                case "text_begin": {
                    receivingSocket.receive(receivingPacket);
                    String date = new String(Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength()));
                    receivingSocket.receive(receivingPacket);
                    String message = new String(Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength()));
                    IH.activityInstance.addTextMessage(message, date);
                    Thread.sleep(10);
                    break;
                }
                case "audio_begin": {
                    receivingSocket.receive(receivingPacket);
                    String date = new String(Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength()));
                    receivingSocket.receive(receivingPacket);
                    byte[] audio_data = Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength());
                    IH.activityInstance.addAudioMessage(audio_data, date);
                    Thread.sleep(10);
                    break;
                }
                case "video_begin": {
                    receivingSocket.receive(receivingPacket);
                    String date = new String(Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength()));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        receivingSocket.receive(receivingPacket);
                        byte[] piece = Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength());
                        if (new String(piece).equals("video_end")) {
                            break;
                        } else {
                            byteArrayOutputStream.write(piece);
                        }
                    }
                    IH.activityInstance.addVideoMessage(byteArrayOutputStream.toByteArray(), date);
                    Thread.sleep(10);
                    break;
                }
                case "ring_begin": {
                    receivingSocket.receive(receivingPacket);
                    IH.activityInstance.ring();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 */


/*

public class NetworkConnector {

    private InetAddress local_address;

    private DatagramSocket sendingSocket, receivingSocket;
    private DatagramPacket receivingPacket;

    private static final int PACKET_SIZE = 65500;
    private static final int AUDIO_PACKET_SIZE = 2000;

    private Thread sendingThread, receivingThread;
    private boolean sending = true,  receiving = true;

    private static boolean send_real_time_data = false;
    private static boolean send_updates = false;

    private byte[] real_time_data = new byte[PACKET_SIZE];
    private byte[] receiving_bytes = new byte[PACKET_SIZE];
    public byte[] audio_data = new byte[AUDIO_PACKET_SIZE];

    private boolean videoDataPlaced = false;
    public boolean pushAudio = true;

    private Queue<String> ringNotificationQueue = new LinkedList<>();
    private Queue<String> textMessageQueue = new LinkedList<>();
    private Queue<String> audioMessageQueue = new LinkedList<>();
    private Queue<String> videoMessageQueue = new LinkedList<>();

    private Queue<String> textDateQueue = new LinkedList<>();
    private Queue<String> audioDateQueue = new LinkedList<>();
    private Queue<String> videoDateQueue = new LinkedList<>();

    private SimpleDateFormat sdf;

    private MainActivity.InstanceHolder IH;


    public NetworkConnector(String ip_address, MainActivity.InstanceHolder instanceHolder){
        this.IH = instanceHolder;
        try {
            sendingSocket = new DatagramSocket(4005);
            local_address = InetAddress.getByName(ip_address);

            receivingSocket = new DatagramSocket(4445);
            receivingPacket = new DatagramPacket(receiving_bytes, receiving_bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        receivingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(receiving){
                    receive();
                }
            }
        });
        receivingThread.start();

        sendingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(sending){
                    send();
                }
            }
        });
        sendingThread.start();

        sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss", Locale.ENGLISH);
    }

    public void addRingMessage(String message){
        ringNotificationQueue.add(message);
    }
    public void addTextMessage(String message){
        textMessageQueue.add(message);
        textDateQueue.add(sdf.format(Calendar.getInstance().getTime()));
    }
    public void addAudioMessage(String message){
        audioMessageQueue.add(message);
        audioDateQueue.add(sdf.format(Calendar.getInstance().getTime()));
    }
    public void addVideoMessage(String message){
        videoMessageQueue.add(message);
        videoDateQueue.add(sdf.format(Calendar.getInstance().getTime()));
    }

    public void setVideoData(byte[] data){
        videoDataPlaced = false;

        System.arraycopy(data, 0, real_time_data, 2, data.length);
        real_time_data[0] = (byte) (data.length);
        real_time_data[1] = (byte) (data.length >> 8);

        videoDataPlaced = true;
    }
    public void setAudioData(byte[] data){
        System.arraycopy(data, 0, real_time_data, real_time_data.length-data.length, data.length);
    }

    private void sendData(byte[] data){
        DatagramPacket sendingPacket = new DatagramPacket(data, data.length, local_address, 4004);
        try {
            sendingSocket.send(sendingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send() {
        try{
            if (send_real_time_data) {
                sendData("real_time_begin".getBytes());
                Thread.sleep(10);
                while(send_real_time_data){
                    if(videoDataPlaced){
                        sendData(real_time_data);
                        Thread.sleep(10);
                        videoDataPlaced = false;
                    }
                }
                Thread.sleep(10);
                sendData("real_time_end".getBytes());
                Thread.sleep(10);
            }
            if(send_updates){
                while(!ringNotificationQueue.isEmpty()) {
                    sendData("ring_begin".getBytes());
                    Thread.sleep(100);
                    sendData(Objects.requireNonNull(ringNotificationQueue.poll()).getBytes());
                    Thread.sleep(100);
                }
                while(!textMessageQueue.isEmpty()){
                    sendData("text_begin".getBytes());
                    Thread.sleep(10);
                    sendData(Objects.requireNonNull(textDateQueue.poll()).getBytes());
                    Thread.sleep(10);
                    sendData(Objects.requireNonNull(textMessageQueue.poll()).getBytes());
                    Thread.sleep(10);
                }
                while(!audioMessageQueue.isEmpty()){
                    sendData("audio_begin".getBytes());
                    Thread.sleep(10);
                    sendData(Objects.requireNonNull(audioDateQueue.poll()).getBytes());
                    Thread.sleep(10);
                    File file = new File(audioMessageQueue.poll());

                    byte[] file_data = new byte[(int) file.length()];

                    try {
                        FileInputStream fis = new FileInputStream(file);
                        if(fis.read(file_data)==-1){
                            Log.e("***", "Audio data is placed.");
                        }
                        fis.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    sendData(file_data);
                    Thread.sleep(10);
                }
                while(!videoMessageQueue.isEmpty()){
                        sendData("video_begin".getBytes());
                        Thread.sleep(100);
                        sendData(Objects.requireNonNull(videoDateQueue.poll()).getBytes());
                        Thread.sleep(10);
                        File file = new File(videoMessageQueue.poll());

                        byte[] file_data = new byte[(int) file.length()];

                        try {
                            FileInputStream fis = new FileInputStream(file);
                            if(fis.read(file_data)==-1){
                                Log.e("***", "Audio data is placed.");
                            }
                            fis.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        int splitter = 65500;
                        int remaining = file_data.length;
                        int index = 0;
                        while(remaining > splitter){
                            byte[] video_data_part = Arrays.copyOfRange(file_data, index, index+splitter);
                            index += splitter;
                            remaining -= splitter;
                            sendData(video_data_part);
                            Thread.sleep(100);
                        }

                        byte[] video_data_part = Arrays.copyOfRange(file_data, index, file_data.length);
                        sendData(video_data_part);
                        Thread.sleep(100);

                        sendData("video_end".getBytes());
                        Thread.sleep(100);
                }
                send_updates = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void receive(){
        try {
            receivingSocket.receive(receivingPacket);
            String tip = new String(Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength()));
            switch (tip) {
                case "check_updates":
                    if (!(ringNotificationQueue.isEmpty() && textMessageQueue.isEmpty() && audioMessageQueue.isEmpty() && videoMessageQueue.isEmpty())) {
                        send_real_time_data = false;
                        send_updates = true;
                    }
                    break;
                case "send_audio":
                    IH.activityInstance.listenAudio();
                    send_real_time_data = true;

                    while (true) {
                        receivingSocket.receive(receivingPacket);
                        byte[] data = Arrays.copyOfRange(receiving_bytes, 0, receivingPacket.getLength());
                        String new_tip = new String(data);
                        if (new_tip.equals("end_audio")) {
                            send_real_time_data = false;
                            break;
                        } else {
                            audio_data = data;
                            pushAudio = true;
                        }
                    }
                    break;
                case "end_audio":
                    send_real_time_data = false;
                    break;
                case "send_video":
                    send_real_time_data = true;
                    break;
                case "end_video":
                    send_real_time_data = false;
                    break;
                case "open_lock":
                    IH.activityInstance.unlock();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */