package com.denizhan.intercom.Network.Tools;

public enum COMMANDS
{

    SEND_AUDIO("send_audio"),
    END_AUDIO("end_audio"),

    CHECK_UPDATES("check_updates"),

    SEND_VIDEO("send_video"),
    END_VIDEO("end_video"),

    OPEN_LOCK("open_lock"),

    REAL_TIME_BEGIN("real_time_begin"),
    REAL_TIME_END("real_time_end"),

    RING("ring_begin"),

    TEXT("text_begin"),
    AUDIO("audio_begin"),
    VIDEO_BEGIN("video_begin"),
    VIDEO_END("video_end");

    private final String type;

    COMMANDS(String str) {

        type = str;
    }
    public String toString()
    {
        return this.type;
    }



}

