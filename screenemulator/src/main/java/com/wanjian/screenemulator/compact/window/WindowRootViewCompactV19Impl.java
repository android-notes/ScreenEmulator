package com.wanjian.screenemulator.compact.window;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WindowRootViewCompactV19Impl extends WindowRootViewCompat {


    WindowRootViewCompactV19Impl() {
        try {
            Class wmClz = Class.forName("android.view.WindowManagerGlobal");
            Method getInstanceMethod = wmClz.getDeclaredMethod("getInstance");
            Object managerGlobal = getInstanceMethod.invoke(wmClz);
            Field mViewsField = wmClz.getDeclaredField("mViews");
            mViewsField.setAccessible(true);
            List<View> rootViews = (List<View>) mViewsField.get(managerGlobal);

            mViewsField.set(managerGlobal, new ArrayList<View>(rootViews) {
                @Override
                public boolean add(View view) {
                    boolean b = super.add(view);
                    notifyWindowAdded(view);
                    return b;
                }

                @Override
                public View remove(int index) {
                    View view = super.remove(index);
                    notifyWindowRemoved(view);
                    return view;
                }

            });

            mViewsField.setAccessible(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
