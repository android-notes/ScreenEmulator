package com.wanjian.emulator.demo;

import android.app.Application;
import android.content.Context;

import com.wanjian.screenemulator.ScreenEmulator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ScreenEmulator.install(this);
    }

}
