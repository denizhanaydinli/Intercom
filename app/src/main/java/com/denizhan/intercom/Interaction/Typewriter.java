package com.denizhan.intercom.Interaction;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.T2S;
import com.denizhan.intercom.R;
//Denizhan
public class Typewriter extends Panel{

    private T2S t2s;

    private Button listen_button;
    private EditText message_field;


    public Typewriter(InstanceHolder ih){
        super(ih, R.id.text_layout);
        this.IH = ih;
        t2s = new T2S((Context) ih.activityInstance.getApplicationContext());
        setViews();
    }

    @Override
    protected void setViews(){
        listen_button = IH.activityInstance.findViewById(R.id.listen_text_button);
        message_field = IH.activityInstance.findViewById(R.id.message_field);
    }

    @Override
    protected void setListeners() {
        listen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2s.readMessage(message_field.getText().toString());
            }
        });
    }

}
