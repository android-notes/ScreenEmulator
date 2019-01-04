package com.wanjian.screenemulator.compact.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import com.wanjian.screenemulator.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ActivityCreateCompact {

    private static ActivityCreateCompact sInstance;

    public static ActivityCreateCompact get(Context context) {
        if (sInstance != null) {
            return sInstance;
        }
        Util.throwIfNull(context);
        if (Build.VERSION.SDK_INT >= 28) {
            //android P
            sInstance = new ActivityCreateCompactV28Impl(context);
        } else {
            sInstance = new ActivityCreateCompactV0Impl();
        }
        return sInstance;
    }

    protected List<ActivityCreateListener> listeners = new ArrayList<>();

    public void addActivityCreateListener(ActivityCreateListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    public void removeActivityCreateListener(ActivityCreateListener listener) {
        if (listener == null) {
            return;
        }
        listeners.remove(listener);
    }

    protected void notifyActivityCreate(Configuration configuration) {
        for (ActivityCreateListener listener : listeners) {
            listener.onActivityCreate(configuration);
        }
    }
}
