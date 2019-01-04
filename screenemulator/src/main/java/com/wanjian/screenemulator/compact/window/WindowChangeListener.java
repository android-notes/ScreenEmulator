package com.wanjian.screenemulator.compact.window;

import android.view.View;

public interface WindowChangeListener {
    void onWindowAdded(View view);

    void onWindowRemoved(View view);
}
