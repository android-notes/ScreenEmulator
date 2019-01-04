package com.wanjian.screenemulator.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;

public class Util {
    public static void throwIfNull(Object o) {
        throwIfNull(o, null);
    }

    public static void throwIfNull(Object o, String reason) {
        if (o == null) {
            throw new NullPointerException(reason);
        }
    }


    public static boolean rootViewOfActivity(List<WeakReference<Activity>> references, View view) {
        for (WeakReference<Activity> reference : references) {
            Activity activity = reference.get();
            if (activity == null) {
                continue;
            }

            if (activity.getWindow().getDecorView() == view) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static void proxyBackground(final View view, final int width, final int height, float scale) {
        final int w = (int) (width * scale + 0.5);
        final int h = (int) (height * scale + 0.5);
        if (w == 0 || h == 0) {
            return;
        }
//        final Path path = new Path();
//        path.moveTo(0, 0);
//        path.lineTo(w, 0);
//        path.lineTo(w, h);
//        path.lineTo(0, h);
//        path.close();

        final Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        final Drawable originDrawable = view.getBackground();
        view.setBackgroundDrawable(new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                originDrawable.draw(canvas);
//                canvas.clipPath(path, Region.Op.DIFFERENCE);
//                canvas.drawColor(Color.BLACK);
                canvas.drawRect(w, 0, view.getWidth(), view.getHeight(), paint);
                canvas.drawRect(0, h, w, view.getHeight(), paint);
            }

            @Override
            public void setAlpha(int alpha) {
                originDrawable.setAlpha(alpha);
            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {
                originDrawable.setColorFilter(colorFilter);
            }

            @Override
            public int getOpacity() {
                return originDrawable.getOpacity();
            }

            @Override
            public void setBounds(int left, int top, int right, int bottom) {
                originDrawable.setBounds(left, top, right, bottom);
            }

            @Override
            public void setBounds(@NonNull Rect bounds) {
                originDrawable.setBounds(bounds);
            }

            @NonNull
            @Override
            public Rect getDirtyBounds() {
                return originDrawable.getDirtyBounds();
            }

            @Override
            public void setChangingConfigurations(int configs) {
                originDrawable.setChangingConfigurations(configs);
            }

            @Override
            public int getChangingConfigurations() {
                return originDrawable.getChangingConfigurations();
            }

            @Override
            public void setDither(boolean dither) {
                originDrawable.setDither(dither);
            }

            @Override
            public void setFilterBitmap(boolean filter) {
                originDrawable.setFilterBitmap(filter);
            }

            @Override
            public boolean isFilterBitmap() {
                return originDrawable.isFilterBitmap();
            }

            @Nullable
            @Override
            public Callback getCallback() {
                return originDrawable.getCallback();
            }

            @Override
            public void invalidateSelf() {
                originDrawable.invalidateSelf();
            }

            @Override
            public void scheduleSelf(@NonNull Runnable what, long when) {
                originDrawable.scheduleSelf(what, when);
            }

            @Override
            public void unscheduleSelf(@NonNull Runnable what) {
                originDrawable.unscheduleSelf(what);
            }

            @Override
            public int getLayoutDirection() {
                return originDrawable.getLayoutDirection();
            }

            @Override
            public boolean onLayoutDirectionChanged(int layoutDirection) {
                return originDrawable.onLayoutDirectionChanged(layoutDirection);
            }

            @Override
            public int getAlpha() {
                return originDrawable.getAlpha();
            }

            @Override
            public void setColorFilter(int color, @NonNull PorterDuff.Mode mode) {
                originDrawable.setColorFilter(color, mode);
            }

            @Override
            public void setTint(int tintColor) {
                originDrawable.setTint(tintColor);
            }

            @Override
            public void setTintList(@Nullable ColorStateList tint) {
                originDrawable.setTintList(tint);
            }

            @Override
            public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
                originDrawable.setTintMode(tintMode);
            }

            @Nullable
            @Override
            public ColorFilter getColorFilter() {
                return originDrawable.getColorFilter();
            }

            @Override
            public void clearColorFilter() {
                originDrawable.clearColorFilter();
            }


            @Override
            public void setHotspot(float x, float y) {
                originDrawable.setHotspot(x, y);
            }

            @Override
            public void setHotspotBounds(int left, int top, int right, int bottom) {
                originDrawable.setHotspotBounds(left, top, right, bottom);
            }

            @Override
            public void getHotspotBounds(@NonNull Rect outRect) {
                originDrawable.getHotspotBounds(outRect);
            }

            @Override
            public boolean isStateful() {
                return originDrawable.isStateful();
            }

            @Override
            public boolean setState(@NonNull int[] stateSet) {
                return originDrawable.setState(stateSet);
            }

            @NonNull
            @Override
            public int[] getState() {
                return originDrawable.getState();
            }

            @Override
            public void jumpToCurrentState() {
                originDrawable.jumpToCurrentState();
            }

            @NonNull
            @Override
            public Drawable getCurrent() {
                return originDrawable.getCurrent();
            }

            @Override
            public boolean setVisible(boolean visible, boolean restart) {
                return originDrawable.setVisible(visible, restart);
            }

            @Override
            public void setAutoMirrored(boolean mirrored) {
                originDrawable.setAutoMirrored(mirrored);
            }

            @Override
            public boolean isAutoMirrored() {
                return originDrawable.isAutoMirrored();
            }

            @Override
            public void applyTheme(@NonNull Resources.Theme t) {
                originDrawable.applyTheme(t);
            }

            @Override
            public boolean canApplyTheme() {
                return originDrawable.canApplyTheme();
            }

            @Nullable
            @Override
            public Region getTransparentRegion() {
                return originDrawable.getTransparentRegion();
            }

            private Method onStateChangeMethod;

            @Override
            protected boolean onStateChange(int[] state) {
                if (onStateChangeMethod == null) {
                    try {
                        onStateChangeMethod = Drawable.class.getDeclaredMethod("onStateChange", int[].class);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    return (boolean) onStateChangeMethod.invoke(originDrawable, state);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            private Method onLevelChangeMethod;

            @Override
            protected boolean onLevelChange(int level) {
                if (onLevelChangeMethod == null) {
                    try {
                        onLevelChangeMethod = Drawable.class.getDeclaredMethod("onLevelChange", int.class);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    return (boolean) onLevelChangeMethod.invoke(originDrawable, level);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            private Method onBoundsChangeMethod;

            @Override
            protected void onBoundsChange(Rect bounds) {
                if (onBoundsChangeMethod == null) {
                    try {
                        onBoundsChangeMethod = Drawable.class.getDeclaredMethod("onBoundsChange", Rect.class);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    onBoundsChangeMethod.invoke(originDrawable, bounds);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public int getIntrinsicWidth() {
                return originDrawable.getIntrinsicWidth();
            }

            @Override
            public int getIntrinsicHeight() {
                return originDrawable.getIntrinsicHeight();
            }

            @Override
            public int getMinimumWidth() {
                return originDrawable.getMinimumWidth();
            }

            @Override
            public int getMinimumHeight() {
                return originDrawable.getMinimumHeight();
            }

            @Override
            public boolean getPadding(@NonNull Rect padding) {
                return originDrawable.getPadding(padding);
            }

            @Override
            public void getOutline(@NonNull Outline outline) {
                originDrawable.getOutline(outline);
            }

            @NonNull
            @Override
            public Drawable mutate() {
                return originDrawable.mutate();
            }

            @Override
            public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs) throws IOException, XmlPullParserException {
                originDrawable.inflate(r, parser, attrs);
            }

            @Override
            public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws IOException, XmlPullParserException {
                originDrawable.inflate(r, parser, attrs, theme);
            }

            @Nullable
            @Override
            public ConstantState getConstantState() {
                return originDrawable.getConstantState();
            }
        });
    }

}
