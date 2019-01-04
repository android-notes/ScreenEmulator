package com.wanjian.screenemulator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DevicesManager {
    private static final String NAME = "emulator_devices_info";
    private static final String SELECTED = "emulator_selected_devices_info";
    private static final String ITEM = "emulator_devices_item_info";

    public static List<DeviceInfo> getDevices(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String strings = sharedPreferences.getString(ITEM, "[]");

        List<DeviceInfo> deviceInfos = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(strings);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String name = jsonObject.getString("name");
                int width = jsonObject.getInt("width");
                int height = jsonObject.getInt("height");
                float size = (float) jsonObject.getDouble("size");
                int densityDpi = jsonObject.getInt("densityDpi");
                float fontScale = jsonObject.getInt("fontScale");
                DeviceInfo deviceInfo = new DeviceInfo(name, width, height, size, densityDpi, fontScale);
                deviceInfos.add(deviceInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deviceInfos;
    }

    public static void addDevices(Context context, DeviceInfo deviceInfo) {
        List<DeviceInfo> deviceInfos = getDevices(context);
        deviceInfos.add(0, deviceInfo);
        JSONArray array = new JSONArray();
        for (DeviceInfo info : deviceInfos) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", info.getName());
                jsonObject.put("width", info.getWidth());
                jsonObject.put("height", info.getHeight());
                jsonObject.put("size", info.getSize());
                jsonObject.put("densityDpi", info.getDensityDpi());
                jsonObject.put("fontScale", info.getFontScale());
                array.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(ITEM, array.toString()).commit();

    }

    public static DeviceInfo getSelectedDevice(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SELECTED, Context.MODE_PRIVATE);
        String strings = sharedPreferences.getString(ITEM, "{}");

        try {
            JSONObject jsonObject = new JSONObject(strings);
            String name = jsonObject.getString("name");
            int width = jsonObject.getInt("width");
            int height = jsonObject.getInt("height");
            float size = (float) jsonObject.getDouble("size");
            int densityDpi = jsonObject.getInt("densityDpi");
            float fontScale = jsonObject.getInt("fontScale");
            DeviceInfo deviceInfo = new DeviceInfo(name, width, height, size, densityDpi, fontScale);
            return deviceInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveSelectedDevice(Context context, DeviceInfo deviceInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SELECTED, Context.MODE_PRIVATE);
        if (deviceInfo == null) {
            sharedPreferences.edit().putString(ITEM, "").commit();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", deviceInfo.getName());
            jsonObject.put("width", deviceInfo.getWidth());
            jsonObject.put("height", deviceInfo.getHeight());
            jsonObject.put("size", deviceInfo.getSize());
            jsonObject.put("densityDpi", deviceInfo.getDensityDpi());
            jsonObject.put("fontScale", deviceInfo.getFontScale());
            sharedPreferences.edit().putString(ITEM, jsonObject.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }


}
