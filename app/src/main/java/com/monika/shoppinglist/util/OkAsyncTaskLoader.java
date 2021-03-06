package com.monika.shoppinglist.util;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public abstract class OkAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
    public static final String TAG = OkAsyncTaskLoader.class.getSimpleName();

    public Exception loadingException;

    public OkAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public T loadInBackground() {
        try {
            return tryLoadInBackground();
        } catch (Exception ex) {
            Log.w(TAG, "Exception", ex);
            loadingException = ex;
            return null;
        }
    }

    public abstract T tryLoadInBackground() throws Exception;
}
