package com.denizhan.intercom.Interaction;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.denizhan.intercom.ExternalTools.InstanceHolder;
import com.denizhan.intercom.MainActivity;
import com.denizhan.intercom.Network.Tools.COMMANDS;
import com.denizhan.intercom.R;
//Denizhan
public class Locker extends Panel {

    private ObjectAnimator lock_animation;

    private String password = "";
    private String current_entered_password = "";
    private boolean locked = true;

    private Button lock_button, ring_button;
    private EditText password_field;

    private CountDownTimer relockCountdownTimer;

    public Locker(String password, InstanceHolder ih){
        super(ih, R.id.lock_layout);
        this.password = password;

        relockCountdownTimer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                lock_animation = ObjectAnimator.ofFloat(lock_button, "translationY", 0);
                lock_animation.setDuration(100);
                lock_animation.start();

                lock_button.setText("KÄ°LÄ°TLÄ°");

                locked = true;
            }
        };
    }

    @Override
    protected void setViews(){
        lock_button = IH.activityInstance.findViewById(R.id.locker_button);
        ring_button = IH.activityInstance.findViewById(R.id.ring_button);
        password_field = IH.activityInstance.findViewById(R.id.password_field);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListeners() {
        View.OnClickListener keypad_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locked) {
                    String pad_name = IH.activityInstance.getResources().getResourceEntryName(v.getId());
                    pad_name = pad_name.substring(4, pad_name.length());
                    int pressed_pad_number = Integer.parseInt(pad_name);
                    if (pressed_pad_number == 10) {
                        if (current_entered_password.equals(password)) {
                            unlock();
                        } else {
                            current_entered_password = "";
                            password_field.setText(current_entered_password);
                            lock_animation = ObjectAnimator.ofFloat(lock_button, "translationX", 0, 15, -15, 6, -6, 0);
                            lock_animation.setDuration(750);
                            lock_animation.start();
                        }
                    } else if (pressed_pad_number == 11) {
                        current_entered_password = "";
                        password_field.setText(current_entered_password);
                    } else {
                        if (current_entered_password.length() < 3) {
                            current_entered_password += pressed_pad_number + "";
                            password_field.setText(current_entered_password);
                        }
                    }
                }
            }
        };

        int starting_id = IH.activityInstance.findViewById(R.id.pad_0).getId();
        for (int i = 0; i < 12; i++) {
            IH.activityInstance.findViewById(starting_id + i).setOnClickListener(keypad_listener);
        }

        lock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ring_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //MainActivity.networkConnector.setCommand(COMMANDS.RING_BEGIN);
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    //MainActivity.networkConnector.setCommand(COMMANDS.IDLE);
                }
                return false;
            }
        });
    }

    public void unlock(){
        relockCountdownTimer.cancel();
        IH.activityInstance.runOnUiThread(new Runnable() {
            public void run() {
                current_entered_password = "";
                password_field.setText(current_entered_password);
                lock_animation = ObjectAnimator.ofFloat(lock_button, "translationY", -15);
                lock_animation.setDuration(250);
                lock_animation.start();
                locked = false;

                lock_button.setText("AÃ‡IK");

                relockCountdownTimer.start();
            }
        });

    }

    public void changePassword(String password){
        this.password = password;
    }
}
