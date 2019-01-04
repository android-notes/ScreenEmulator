package com.wanjian.screenemulator;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.wanjian.screenemulator.compact.activity.ActivityCreateCompact;
import com.wanjian.screenemulator.compact.activity.ActivityCreateListener;
import com.wanjian.screenemulator.compact.window.WindowChangeListener;
import com.wanjian.screenemulator.compact.window.WindowRootViewCompat;
import com.wanjian.screenemulator.utils.DeviceInfo;
import com.wanjian.screenemulator.utils.DevicesManager;
import com.wanjian.screenemulator.utils.Util;

import java.lang.ref.WeakReference;
import java.util.List;

public class ScreenEmulator {
    public static void install(Application application) {
        final DeviceInfo deviceInfo = DevicesManager.getSelectedDevice(application);
        if (deviceInfo == null) {
            return;
        }
        ActivityFetcher.install(application);
        DisplayMetrics metrics = application.getResources().getDisplayMetrics();
//        final int screenWidth = metrics.widthPixels;
//        int screenHeight = metrics.heightPixels;
        final float ppi = (metrics.ydpi + metrics.xdpi) / 2;
        WindowRootViewCompat.get(application).addWindowChangeListener(new WindowChangeListener() {
            @Override
            public void onWindowAdded(final View view) {
                List<WeakReference<Activity>> references = ActivityFetcher.getActivities();
                if (Util.rootViewOfActivity(references, view) == false) {
                    return;
                }
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        ViewGroup group = ((ViewGroup) view);
                        for (int i = 0; i < group.getChildCount(); i++) {
                            View child = group.getChildAt(i);
                            ViewGroup.LayoutParams params = child.getLayoutParams();
                            if (params == null) {
                                continue;
                            }
                            if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                                params.width = deviceInfo.getWidth();
                                child.requestLayout();
                            }
                            if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                                params.height = deviceInfo.getHeight();
                                child.requestLayout();
                            }
                            child.setPivotX(0);
                            child.setPivotY(0);
                            child.setScaleX(ppi / deviceInfo.getSize());
                            child.setScaleY(ppi / deviceInfo.getSize());

                        }
                        Util.proxyBackground(view, deviceInfo.getWidth(), deviceInfo.getHeight(), ppi / deviceInfo.getSize());
                    }
                });

            }

            @Override
            public void onWindowRemoved(View view) {

            }
        });

        ActivityCreateCompact.get(application).addActivityCreateListener(new ActivityCreateListener() {
            @Override
            public void onActivityCreate(Configuration configuration) {
                configuration.densityDpi = deviceInfo.getDensityDpi();
                configuration.fontScale = deviceInfo.getFontScale();
            }
        });
    }


}
