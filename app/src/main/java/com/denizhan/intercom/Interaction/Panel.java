package com.denizhan.intercom.Interaction;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import com.denizhan.intercom.ExternalTools.InstanceHolder;

public abstract class Panel {

    InstanceHolder IH;
    private ConstraintLayout constraintLayout;

    public Panel(InstanceHolder ih, int id){
        this.IH = ih;
        this.constraintLayout = (ConstraintLayout) ih.activityInstance.findViewById(id);
        setViews();
        setListeners();
    }

    public void hide(){
        constraintLayout.setVisibility(View.GONE);
    }

    public void show(){
        constraintLayout.setVisibility(View.VISIBLE);
    }

    protected abstract void setViews();
    protected abstract void setListeners();
}
