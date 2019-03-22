package com.denizhan.intercom.Network.Tools;

import java.util.Arrays;

public class ByteSplitter {

    //bytelari parcalara ayirma islemi yapacak
    //  Arrays.copyOfRange();
    private byte[] data;
    private int PIECE_SIZE;
    private int REMAINING;
    private int INDEX;

    public ByteSplitter(byte[] data, int pieceSize)
    {
        this.data = data;
        this.PIECE_SIZE = pieceSize;
        this.REMAINING = data.length;
        this.INDEX = 0;
    }

    public byte[] nextData(){
        if(this.REMAINING > 0){
            if(this.REMAINING > this.PIECE_SIZE){
                byte[] data_piece = Arrays.copyOfRange(data, this.INDEX, this.INDEX + this.PIECE_SIZE);
                this.INDEX += this.PIECE_SIZE;
                this.REMAINING -= this.PIECE_SIZE;
                return data_piece; // parÃ§a boyutu kadar olan datayÄ± ver
            }else{
                byte[] data_piece = Arrays.copyOfRange(data, this.INDEX, this.INDEX + this.REMAINING);
                this.INDEX += this.REMAINING;
                this.REMAINING = 0;
                return data_piece; // parÃ§a boyutu kadar olan datayÄ± ver
            }
        }
        return null;
    }

}