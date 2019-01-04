package com.wanjian.screenemulator;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ActivityFetcher {
    private static List<WeakReference<Activity>> references = new ArrayList<>();

    public static List<WeakReference<Activity>> getActivities() {
        return references;
    }

    public static void install(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                references.add(new WeakReference<>(activity));
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                for (int i = references.size() - 1; i > -1; i--) {
                    WeakReference ref = references.get(i);
                    if (ref.get() == activity) {
                        references.remove(i);
                        return;
                    }
                }
            }
        });
    }
}
