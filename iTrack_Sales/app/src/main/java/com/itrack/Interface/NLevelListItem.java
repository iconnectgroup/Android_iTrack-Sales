package com.itrack.Interface;

/**
 * Created by Sandip on 31-01-2017.
 */
import android.view.View;

public interface NLevelListItem {

    public boolean isExpanded();
    public void toggle();
    public NLevelListItem getParent();
    public View getView();
}