package com.denizhan.intercom.Network;

/*
    Yazacak Olan: Nehir
    Açıklama: UDP protokolünde byte gönderme
*/

public class UDPSender {

    // Alici adresi
    private InetAddress local_address;

    // Veri gonderme soketi
    private DatagramSocket sendingSocket;

    // Veri paketini tutmak için datagram paketi
    private DatagramPacket sendingPacket;

    public UDPSender(String ipAdress){
         try {
            sendingSocket = new DatagramSocket(4005);
            local_address = InetAddress.getByName(ipAdress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data){

        // paketi veri boyutuna göre guncelle
        sendingPacket = new DatagramPacket(data, data.length, local_address, 4004);
        try {
            sendingSocket.send(sendingPacket); // sokete veri yollama
        } catch (IOException e) {
            e.printStackTrace();
            // OlaGan hata paketin veri boyutunun udp buyuk olması
           }
    }
}