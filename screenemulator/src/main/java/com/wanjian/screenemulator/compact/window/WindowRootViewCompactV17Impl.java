package com.wanjian.screenemulator.compact.window;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WindowRootViewCompactV17Impl extends WindowRootViewCompactV16Impl {


    WindowRootViewCompactV17Impl() {
        try {
            Class wmClz = Class.forName("android.view.WindowManagerGlobal");
            Method getInstanceMethod = wmClz.getDeclaredMethod("getInstance");
            Object windowManagerGlobal = getInstanceMethod.invoke(wmClz);
            Field viewsField = wmClz.getDeclaredField("mViews");
            viewsField.setAccessible(true);
            observerViews(viewsField, windowManagerGlobal);
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
