package com.monika.shoppinglist.util;


import android.util.Log;

import okhttp3.Call;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class OkCancellableCall {
    public static final String TAG = OkCancellableCall.class.getSimpleName();
    private final Call mCall;

    public OkCancellableCall(Call call) {
        mCall = call;
    }

    public void cancel() {
        if (mCall != null && !mCall.isCanceled()) {
            Log.d(TAG, "Cancelling the call");
            mCall.cancel();
        }
    }
}

