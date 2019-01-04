package com.wanjian.screenemulator.utils;

public class DeviceInfo {
    private String name;
    private int width;
    private int height;
    private float size;
    private int densityDpi;
    private float fontScale = 1;

    public DeviceInfo() {
    }

    public DeviceInfo(String name, int width, int height, float size, int densityDpi, float fontScale) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.size = size;
        this.densityDpi = densityDpi;
        this.fontScale = fontScale;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSize() {
        return size;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public float getFontScale() {
        return fontScale;
    }

    public boolean equals(DeviceInfo other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }

        return name != null && name.equals(other.name)
                && width == other.width
                && height == other.height
                && Math.abs(size - other.size) < 0.0001
                && densityDpi == other.densityDpi
                && Math.abs(fontScale - other.fontScale) < 0.0001;

    }

    @Override
    public String toString() {
        return new StringBuilder().append(name)
                .append(" : ")
                .append(width)
                .append("x")
                .append(height)
                .append("  ppi:")
                .append(size)
                .append("  densityDpi:")
                .append(densityDpi)
                .append("  fontScale:")
                .append(fontScale)
                .toString();
    }
}
