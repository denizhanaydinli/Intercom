package com.denizhan.intercom.ExternalTools;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import java.util.Locale;

public class T2S {

    private TextToSpeech textToSpeech;

    public T2S(Context context){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                        }
                        @Override
                        public void onDone(String utteranceId) {

                        }
                        @Override
                        public void onError(String utteranceId) { }
                    });
                    textToSpeech.setSpeechRate(0.8f);
                    textToSpeech.setLanguage(Locale.forLanguageTag("tr-TR"));
                }
            }
        });
    }

    public void readMessage(String message){
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "1234567");
    }

    public void destroy(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
