package com.wanjian.screenemulator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wanjian.screenemulator.utils.DeviceInfo;
import com.wanjian.screenemulator.utils.DevicesManager;

public class AddDeviceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.emulator_add_device_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText name = view.findViewById(R.id.name);
        final EditText width = view.findViewById(R.id.width);
        final EditText height = view.findViewById(R.id.height);
        final EditText size = view.findViewById(R.id.size);
        final EditText densityDpi = view.findViewById(R.id.densityDpi);
        final EditText fontScale = view.findViewById(R.id.fontScale);
        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = name.getText().toString();
                int deviceWidth = toInt(width.getText().toString(), 0);
                int deviceHeight = toInt(height.getText().toString(), 0);
                float deviceSize = toFloat(size.getText().toString(), 0);
                int deviceDensityDpi = toInt(densityDpi.getText().toString(), 0);
                float deviceFontScale = toFloat(fontScale.getText().toString(), 0);

                if (deviceName.isEmpty()) {
                    Toast.makeText(getContext(), "请填写设备名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (deviceWidth <= 0 || deviceHeight <= 0 || deviceSize <= 0 || deviceDensityDpi <= 0 || deviceFontScale <= 0) {
                    Toast.makeText(getContext(), "参数错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                DevicesManager.addDevices(getContext(), new DeviceInfo(deviceName, deviceWidth, deviceHeight, deviceSize, deviceDensityDpi, deviceFontScale));
                Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });

    }

    private int toInt(String num, int defaultValue) {

        try {
            return Integer.valueOf(num);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private float toFloat(String num, float defaultValue) {

        try {
            return Float.valueOf(num);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    private View.OnClickListener listener;

    public void setOnAddClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

}
