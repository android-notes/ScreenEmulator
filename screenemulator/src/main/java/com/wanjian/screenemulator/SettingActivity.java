package com.wanjian.screenemulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class SettingActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.emulator_setting_activity);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final DeviceListFragment devicesFragment = new DeviceListFragment();
        fragmentManager.beginTransaction().replace(R.id.container, devicesFragment).commit();

        devicesFragment.setOnAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AddDeviceFragment fragment = new AddDeviceFragment();
                fragmentManager.beginTransaction().add(R.id.container, fragment).show(fragment).commit();

                fragment.setOnAddClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        devicesFragment.refresh();
                    }
                });
            }
        });
    }
}
