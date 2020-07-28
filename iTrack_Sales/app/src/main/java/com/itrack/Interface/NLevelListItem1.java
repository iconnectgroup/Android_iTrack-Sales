package com.itrack.Interface;

/**
 * Created by Sandip on 31-01-2017.
 */
import android.view.View;

public interface NLevelListItem1 {

    public boolean isExpanded();
    public void toggle();
    public NLevelListItem1 getParent();
    public View getView();
}