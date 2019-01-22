package com.denizhan.intercom.ExternalTools;

import android.animation.ObjectAnimator;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.denizhan.intercom.R;
//Denizhan
public class Locker {

    private InstanceHolder IH;

    private ObjectAnimator lock_animation;

    private String password = "";
    private String current_entered_password = "";
    private boolean locked = true;

    private ImageButton lock_button;
    private EditText password_field;

    public Locker(String password, InstanceHolder ih){
        this.password = password;
        this.IH = ih;
        setViews();
    }

    public void setViews(){
        lock_button = IH.activityInstance.findViewById(R.id.lock_button);
        password_field = IH.activityInstance.findViewById(R.id.password_field);

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
    }

    private void unlock(){
        if(locked){
            IH.activityInstance.runOnUiThread(new Runnable() {
                public void run() {
                    current_entered_password = "";
                    password_field.setText(current_entered_password);
                    lock_animation = ObjectAnimator.ofFloat(lock_button, "translationY", 0, -15);
                    lock_animation.setDuration(250);
                    lock_animation.start();
                    locked = false;
                    new CountDownTimer(750, 750) {
                        public void onTick(long millisUntilFinished) { }
                        public void onFinish() {
                            lock_animation = ObjectAnimator.ofFloat(lock_button, "translationY", 0);
                            lock_animation.setDuration(100);
                            lock_animation.start();

                            locked = true;
                        }
                    }.start();
                }
            });
        }

    }

    public boolean isCorrect(String password){
        if(password.equals(this.password)){
            return true;
        }else{
            return false;
        }
    }

    public void changePassword(String password){
        this.password = password;
    }

}
