package com.denizhan.intercom.Interaction;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.ExternalTools.T2S;
import com.denizhan.intercom.R;

import org.w3c.dom.Text;

//Denizhan
public class Typewriter extends Panel{

    private T2S t2s;

    private Button listen_text_button, cancel_text_button, send_text_button;
    private ProgressBar listen_progressbar;
    private EditText message_field;
    private TextView remaining_letter_count;

    private int maximumLength;


    public Typewriter(InstanceHolder ih, int maximumLength){
        super(ih, R.id.text_layout);
        this.maximumLength = maximumLength;
        setTools();
    }

    @Override
    protected void setViews(){
        listen_text_button = IH.activityInstance.findViewById(R.id.listen_text_button);
        listen_text_button.setVisibility(View.INVISIBLE);
        message_field = IH.activityInstance.findViewById(R.id.message_field);
        cancel_text_button = IH.activityInstance.findViewById(R.id.cancel_text_button);
        cancel_text_button.setVisibility(View.INVISIBLE);
        send_text_button = IH.activityInstance.findViewById(R.id.send_text_button);
        send_text_button.setVisibility(View.INVISIBLE);
        listen_progressbar = IH.activityInstance.findViewById(R.id.listen_progressbar);
        listen_progressbar.setVisibility(View.INVISIBLE);
        remaining_letter_count = IH.activityInstance.findViewById(R.id.remaining_letter_count);
    }

    @Override
    protected void setListeners() {
        listen_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2s.readMessage(message_field.getText().toString());
            }
        });

        cancel_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_field.setText("");
                t2s.stop();
            }
        });

        send_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_field.setText("");
                t2s.stop();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remaining_letter_count.setText(s.length() + " / " + maximumLength);
                if(s.length() > 0){
                    cancel_text_button.setVisibility(View.VISIBLE);
                    listen_text_button.setVisibility(View.VISIBLE);
                    send_text_button.setVisibility(View.VISIBLE);
                }else{
                    cancel_text_button.setVisibility(View.INVISIBLE);
                    listen_text_button.setVisibility(View.INVISIBLE);
                    send_text_button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        message_field.addTextChangedListener(textWatcher);
    }

    private void setTools(){
        t2s = new T2S((Context) IH.activityInstance.getApplicationContext());
        t2s.setVisuals(listen_progressbar, listen_text_button);

        remaining_letter_count.setText("0 / " + maximumLength);

        message_field.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maximumLength)});
    }

}
