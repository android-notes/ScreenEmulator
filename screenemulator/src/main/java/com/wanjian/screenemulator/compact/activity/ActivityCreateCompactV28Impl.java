package com.wanjian.screenemulator.compact.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import me.weishu.reflection.Reflection;

public class ActivityCreateCompactV28Impl extends ActivityCreateCompact {

    public ActivityCreateCompactV28Impl(Context context) {
        try {
            init(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init(Context context) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        Reflection.unseal(context);

        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getDeclaredMethod("currentActivityThread").invoke(null);

        Field mhField = activityThreadClass.getDeclaredField("mH");
        mhField.setAccessible(true);
        Handler mhHandler = (Handler) mhField.get(activityThread);
        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        final Handler.Callback originCallback = (Handler.Callback) callbackField.get(mhHandler);
        callbackField.set(mhHandler, new Handler.Callback() {
            final int EXECUTE_TRANSACTION = 159;

            @Override
            public boolean handleMessage(Message msg) {

                if (msg.what == EXECUTE_TRANSACTION) {
                    try {
                        Object clientTransaction = msg.obj;
                        Method getCallbacksMethod = clientTransaction.getClass().getDeclaredMethod("getCallbacks");
                        getCallbacksMethod.setAccessible(true);
                        List<Object> transactionItems = (List<Object>) getCallbacksMethod.invoke(clientTransaction);
                        Object launchActivityItem = transactionItems.get(0);
                        Field configurationField = launchActivityItem.getClass().getDeclaredField("mOverrideConfig");
                        configurationField.setAccessible(true);
                        Configuration configuration = (Configuration) configurationField.get(launchActivityItem);

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
