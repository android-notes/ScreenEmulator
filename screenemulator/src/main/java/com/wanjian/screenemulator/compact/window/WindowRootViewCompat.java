package com.wanjian.screenemulator.compact.window;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.wanjian.screenemulator.utils.Util;

import java.util.ArrayList;
import java.util.List;

/*
 * get the root view of all windows
 * <p>
 * when you add view by windowManager.addView(), the root view may not be a DecorView
 * <p>
 * <p>
 * There are some differences when you want to get the root view
 * <p>
 * 4.0.3_r1    WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.0.4       WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.1.1       WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.1.2       WindowManagerImpl      private View[] mViews;
 * <p>
 * 4.2_r1      WindowManagerGlobal    private View[] mViews
 * <p>
 * 4.2.2 r1    WindowManagerGlobal    private View[] mViews
 * <p>
 * 4.3_r2.1    WindowManagerGlobal    private View[] mViews;
 * <p>
 * 4.4_r1      WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 4.4.2_r1    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 5.0.0_r2    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 6.0.0_r1    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 7.0.0_r1    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 * <p>
 * 8.0.0_r4    WindowManagerGlobal    private final ArrayList&lt;View&gt; mViews
 */
public abstract class WindowRootViewCompat {

    private static WindowRootViewCompat sInstance;

    public static WindowRootViewCompat get(Context context) {
        if (sInstance != null) {
            return sInstance;
        }

        Util.throwIfNull(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sInstance = new WindowRootViewCompactV19Impl();
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            sInstance = new WindowRootViewCompactV17Impl();
        } else {
            sInstance = new WindowRootViewCompactV16Impl(context.getApplicationContext());
        }
        return sInstance;
    }

    protected List<WindowChangeListener> listeners = new ArrayList<>();

    public void addWindowChangeListener(WindowChangeListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    public void removeWindowChangeListener(WindowChangeListener listener) {
        if (listener == null) {
            return;
        }
        listeners.remove(listener);
    }

    protected void notifyWindowAdded(View view) {
        for (WindowChangeListener listener : listeners) {
            listener.onWindowAdded(view);
        }
    }

    protected void notifyWindowRemoved(View view) {
        for (WindowChangeListener listener : listeners) {
            listener.onWindowRemoved(view);
        }
    }
}