package com.denizhan.intercom.Network.Tools;

public enum COMMANDS
{

    IDLE("idle"),

    UNLOCK_BEGIN("unlock_begin"),

    RING_BEGIN("ring_begin"),

    REAL_TIME_VIDEO_BEGIN("real_time_video_begin"),

    REAL_TIME_AUDIO_BEGIN("real_time_audio_begin"),

    VIDEO_BEGIN("video_begin"),
    VIDEO_END("video_end"),

    AUDIO_BEGIN("audio_begin"),
    AUDIO_END("audio_end"),

    TEXT_BEGIN("text_begin"),
    TEXT_END("text_end");

    private final String type;

    COMMANDS(String str) {

        type = str;
    }

    public String toString()
    {
        return this.type;
    }

    public boolean eqauls(COMMANDS command){
        if(this.type.equals(command.toString())){
            return true;
        }

        return false;
    }

    public boolean eqauls(String command){
        if(this.type.equals(command)){
            return true;
        }

        return false;
    }

    public static boolean compare(String str, COMMANDS command){
        if(str.equals(command.toString())){
            return true;
        }
        return false;
    }

    public static boolean compare(COMMANDS command, String str){
        if(str.equals(command.toString())){
            return true;
        }
        return false;
    }

    public byte[] getBytes(){
        return this.type.getBytes();
    }

}

