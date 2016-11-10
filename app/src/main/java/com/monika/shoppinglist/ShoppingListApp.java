package com.monika.shoppinglist;

import android.app.Application;
import android.util.Log;

import com.monika.shoppinglist.net.ObjectRestClient;
import com.monika.shoppinglist.service.ObjectManager;

/**
 * Created by Mónika on 2016. 11. 10..
 */
public class ShoppingListApp extends Application {
    public static final String TAG = ShoppingListApp.class.getSimpleName();
    private ObjectManager mObjectManager;
    private ObjectRestClient mObjectRestClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mObjectManager = new ObjectManager(this);
        mObjectRestClient = new ObjectRestClient(this);
        mObjectManager.setObjectRestClient(mObjectRestClient);
    }

    public ObjectManager getObjectManager() {
        return mObjectManager;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }
}
