package com.denizhan.intercom.Network;

/*
    Yazacak Olan: Nehir
    AÃ§Ä±klama: UDP protokolÃ¼nde byte gÃ¶nderme
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.denizhan.intercom.Network.Tools.COMMANDS;

public class UDPSender {

    // Alici adresi
    private InetAddress local_address;

    // Veri gonderme soketi
    private DatagramSocket sendingSocket;

    // Veri paketini tutmak iÃ§in datagram paketi
    private DatagramPacket sendingPacket;

    public UDPSender(String ipAdress){
        try {
            sendingSocket = new DatagramSocket(4005);
            //byte[] ipAddr = new byte[]{127, 0, 0, 1};
            // InetAddress addr = InetAddress.getByAddress(ipAddr);
            local_address = InetAddress.getByName("192.168.43.165");//karsi tarafin ipsi
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data){

        // paketi veri boyutuna gÃ¶re guncelle
        sendingPacket = new DatagramPacket(data, data.length, local_address, 4004);
        try {
            sendingSocket.send(sendingPacket); // sokete veri yollama
        } catch (IOException e) {
            e.printStackTrace();
            // OlaGan hata paketin veri boyutunun udp buyuk olmasÄ±
        }
    }
    public void sendCommand(COMMANDS command){
        sendingPacket = new DatagramPacket(command.toString().getBytes(), command.toString().getBytes().length, local_address, 4004); // paketi veri boyutuna gÃ¶re gÃ¼ncelle
        try {
            sendingSocket.send(sendingPacket); // sokete veriyi gÃ¶nder
        } catch (IOException e) {
            e.printStackTrace();
            // OlaÄŸan hata paketin veri boyutunun udp bÃ¼yÃ¼k olmasÄ±
        }
    }

}