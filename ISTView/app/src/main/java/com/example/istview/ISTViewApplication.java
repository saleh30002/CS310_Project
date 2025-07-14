package com.example.istview;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ISTViewApplication extends Application {

    ExecutorService srv = Executors.newCachedThreadPool();
}
