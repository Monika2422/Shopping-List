package com.monika.shoppinglist.service;

import android.content.Context;
import android.util.Log;

import com.monika.shoppinglist.content.Object;
import com.monika.shoppinglist.net.ObjectRestClient;
import com.monika.shoppinglist.util.OkCancellableCall;
import com.monika.shoppinglist.util.OnErrorListener;
import com.monika.shoppinglist.util.OnSuccessListener;

import java.util.List;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class ObjectManager {
    private static final String TAG = ObjectManager.class.getSimpleName();

    private List<Object> mObjects;
    private OnObjectUpdateListener mOnUpdate;

    private final Context mContext;
    private ObjectRestClient mObjectRestClient;

    public ObjectManager(Context context) {
        mContext = context;
    }

    public List<Object> getObjects() throws Exception { //sync method
        Log.d(TAG, "getObjects...");
        mObjects = mObjectRestClient.getAll();
        return mObjects;
    }

    public ObjectLoader getObjectLoader() {
        Log.d(TAG, "getObjectLoader...");
        return new ObjectLoader(mContext, mObjectRestClient);
    }

    public void addObject() {
//        mObjects.add(new Object("Added object " + mObjects.size()));
//        if (mOnUpdate != null) {
//            mOnUpdate.updated();
//        }
    }

    public void setOnUpdate(OnObjectUpdateListener onUpdate) {
        mOnUpdate = onUpdate;
    }

    public void setObjectRestClient(ObjectRestClient objectRestClient) {
        mObjectRestClient = objectRestClient;
    }

    public OkCancellableCall getObjectsAsync(OnSuccessListener<List<Object>> onSuccessListener, OnErrorListener onErrorListener) {
        return mObjectRestClient.getAllAsync(onSuccessListener, onErrorListener);
    }

    public interface OnObjectUpdateListener {
        void updated();
    }
}
