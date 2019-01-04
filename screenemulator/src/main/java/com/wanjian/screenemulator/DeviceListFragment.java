package com.wanjian.screenemulator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.wanjian.screenemulator.utils.DeviceInfo;
import com.wanjian.screenemulator.utils.DevicesManager;

import java.util.List;

public class DeviceListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.emulator_devices_list_fragment, container, false);
    }

    private TextView add;
    private DeviceInfo selectedDevice;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView = view.findViewById(R.id.listView);
        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });

        selectedDevice = DevicesManager.getSelectedDevice(getContext());
        listView.setAdapter(adapter);

        setData();

        view.findViewById(R.id.tips).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
//                metrics.widthPixels
            }
        });

    }

    private void setData() {
        List<DeviceInfo> deviceInfos = DevicesManager.getDevices(getContext());

        if (deviceInfos.isEmpty()) {
            addDefaultDevices(deviceInfos);
            for (DeviceInfo deviceInfo : deviceInfos) {
                DevicesManager.addDevices(getContext(), deviceInfo);
            }
        }

        adapter.setDeviceInfos(deviceInfos);
    }

    private void addDefaultDevices(List<DeviceInfo> deviceInfos) {
        deviceInfos.add(new DeviceInfo("小米6", 1080, 1920, 428, 480, 1));
//        deviceInfos.add(new DeviceInfo("华为P10", 1080, 1920, 432, 1, 1));
//        deviceInfos.add(new DeviceInfo("红米6 ", 720, 1440, 295, 1, 1));
//        deviceInfos.add(new DeviceInfo("一加6", 1080, 2280, 400, 1, 1));
//        deviceInfos.add(new DeviceInfo("华为maters保时捷", 1440, 2880, 538, 1, 1));
    }


    private DeviceAdapter adapter = new DeviceAdapter();
    private View.OnClickListener listener;

    public void setOnAddClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void refresh() {
        setData();
    }

    class DeviceAdapter extends BaseAdapter {
        private List<DeviceInfo> deviceInfos;

        public void setDeviceInfos(List<DeviceInfo> deviceInfos) {
            this.deviceInfos = deviceInfos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return deviceInfos == null ? 0 : deviceInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.emulator_device_item, parent, false);
            }

            TextView infoItem = convertView.findViewById(R.id.title);
            final CheckBox checkBox = convertView.findViewById(R.id.checkbox);

            final DeviceInfo deviceInfo = deviceInfos.get(position);

            infoItem.setText(deviceInfo.toString());

            if (selectedDevice != null && selectedDevice.equals(deviceInfo)) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        selectedDevice = deviceInfo;
                        DevicesManager.saveSelectedDevice(getContext(), selectedDevice);
                        notifyDataSetChanged();
                    } else {
                        selectedDevice = null;
                        DevicesManager.saveSelectedDevice(getContext(), selectedDevice);
                        notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }
    }
}
