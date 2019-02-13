package com.denizhan.intercom.ExternalTools;

import com.denizhan.intercom.MainActivity;
//Denizhan
public class InstanceHolder {
    public MainActivity activityInstance;
    //bundle da ekleyebiliriz ilerde

    public InstanceHolder(MainActivity activity){
        this.activityInstance = activity;
    }
}