package com.denizhan.intercom.Network;

/*
    Yazacak Olan: Nehir
    Açıklama: UDP protokolünde byte alma
*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class UDPReceiver {


        // Veri almak icin soket
        private DatagramSocket receivingSocket;

        // Veri paketini tutmak icin datagram paketi
        private DatagramPacket receivingPacket;

        private static final int PACKET_SIZE = 65500;
        private byte[] receivingBytes = new byte[PACKET_SIZE];

        public UDPReceiver(){
            try {
                receivingSocket = new DatagramSocket(4445);
                receivingPacket = new DatagramPacket(receivingBytes, receivingBytes.length);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        public byte[] receive(){
            try {
                receivingSocket.receive(receivingPacket); // paketi kabul et
                return Arrays.copyOfRange(receivingBytes,0, receivingPacket.getLength()); // alinan paketi dondur
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new byte[0];
        }


}
