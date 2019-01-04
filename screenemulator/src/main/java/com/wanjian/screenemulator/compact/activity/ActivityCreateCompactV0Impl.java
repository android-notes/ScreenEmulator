package com.wanjian.screenemulator.compact.activity;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ActivityCreateCompactV0Impl extends ActivityCreateCompact {
    public ActivityCreateCompactV0Impl() {
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getDeclaredMethod("currentActivityThread").invoke(null);

        Field mhField = activityThreadClass.getDeclaredField("mH");
        mhField.setAccessible(true);
        Handler mhHandler = (Handler) mhField.get(activityThread);
        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        final Handler.Callback originCallback = (Handler.Callback) callbackField.get(mhHandler);
        callbackField.set(mhHandler, new Handler.Callback() {
            final int LAUNCH_ACTIVITY = 100;

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == LAUNCH_ACTIVITY) {
                    try {
                        Object obj = msg.obj;
                        Field configurationField = obj.getClass().getDeclaredField("overrideConfig");
                        configurationField.setAccessible(true);
                        Configuration configuration = (Configuration) configurationField.get(obj);
                        notifyActivityCreate(configuration);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (originCallback != null) {
                    return originCallback.handleMessage(msg);
                }
                return false;
            }
        });
    }

}
