package com.monika.shoppinglist.service;

import android.content.Context;
import android.util.Log;

import com.monika.shoppinglist.content.Object;
import com.monika.shoppinglist.net.ObjectRestClient;
import com.monika.shoppinglist.util.OkAsyncTaskLoader;

import java.util.List;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class ObjectLoader extends OkAsyncTaskLoader<List<Object>> {
    private static final String TAG = ObjectLoader.class.getSimpleName();
    private final ObjectRestClient mObjectRestClient;
    private List<Object> objectList;

    public ObjectLoader(Context context, ObjectRestClient noteRestClient) {
        super(context);
        mObjectRestClient = noteRestClient;
    }

    @Override
    public List<Object> tryLoadInBackground() throws Exception {
        Log.d(TAG, "tryLoadInBackground");
        objectList = mObjectRestClient.getAll();
        return objectList;
    }

    @Override
    protected void onStartLoading() {
        if (objectList != null) {
            Log.d(TAG, "onStartLoading - deliver result");
            deliverResult(objectList);
        }

        if (takeContentChanged() || objectList == null) {
            Log.d(TAG, "onStartLoading - force load");
            forceLoad();
        }
    }
}