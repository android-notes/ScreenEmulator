package com.wanjian.screenemulator.compact.window;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class WindowRootViewCompactV16Impl extends WindowRootViewCompat {
    private View[] lastViews = new View[0];

    WindowRootViewCompactV16Impl() {
    }

    WindowRootViewCompactV16Impl(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Object wm = getOuter(windowManager);
        getWindowViews(wm);

    }


    private Object getOuter(Object innerWM) {
        try {
            Field parentField = innerWM.getClass().getDeclaredField("mWindowManager");
            parentField.setAccessible(true);
            Object outerWM = parentField.get(innerWM);
            parentField.setAccessible(false);
            return outerWM;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void getWindowViews(final Object windowManager) {
        try {
            Class clz = windowManager.getClass();
            Field field = clz.getDeclaredField("mViews");
            field.setAccessible(true);
            observerViews(field, windowManager);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void observerViews(final Field field, final Object windowManager) throws IllegalAccessException {
        View[] views = (View[]) field.get(windowManager);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    observerViews(field, windowManager);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 16);
        if (views == lastViews) {
            return;
        }

        List<View> added = diff(lastViews, views);
        for (View view : added) {
            notifyWindowAdded(view);
        }


        List<View> removed = diff(views, lastViews);
        for (View view : removed) {
            notifyWindowRemoved(view);
        }

        lastViews = views;
    }

    private List<View> diff(View[] views1, View[] views2) {
        List<View> views = new ArrayList<>();
        if (views2 == null) {
            return views;
        }
        for (View view : views2) {
            if (!contain(view, views1)) {
                views.add(view);
            }
        }

        return views;
    }

    private boolean contain(View view, View[] views) {
        if (views == null) {
            return false;
        }
        for (View v : views) {
            if (v == view) {
                return true;
            }
        }
        return false;
    }

}