package com.denizhan.intercom.ExternalTools;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Locale;

public class T2S {

    private TextToSpeech textToSpeech;
    private ProgressBar progressBar;
    private Button button;

    public T2S(final Context context){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            progressBar.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                        @Override
                        public void onDone(String utteranceId) {
                            button.post(new Runnable() {
                                @Override
                                public void run() {
                                    button.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        @Override
                        public void onError(String utteranceId) {
                            button.post(new Runnable() {
                                @Override
                                public void run() {
                                    button.setVisibility(View.VISIBLE);
                                }
                            });
                            progressBar.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                            Log.e("***", utteranceId + " error");
                        }
                    });
                    textToSpeech.setSpeechRate(0.8f);
                    textToSpeech.setLanguage(Locale.forLanguageTag("tr-TR"));
                }
            }
        });
    }

    public void readMessage(String message){
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
        }
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "1234567");
    }

    public void stop(){
        textToSpeech.stop();
    }

    public void destroy(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    public void setVisuals(ProgressBar progressBar, Button button) {
        this.progressBar = progressBar;
        this.button = button;
    }
}
