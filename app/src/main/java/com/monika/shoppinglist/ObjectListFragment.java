package com.monika.shoppinglist;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.monika.shoppinglist.content.Object;
import com.monika.shoppinglist.util.OkAsyncTask;
import com.monika.shoppinglist.util.OkAsyncTaskLoader;
import com.monika.shoppinglist.util.OkCancellableCall;
import com.monika.shoppinglist.util.OnErrorListener;
import com.monika.shoppinglist.util.OnSuccessListener;
import com.monika.shoppinglist.withget.ObjectListAdapter;

import java.util.List;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class ObjectListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Object>> {

    public static final String TAG = ObjectListFragment.class.getSimpleName();
    private static final int OBJECT_LOADER_ID = 1;
    private ShoppingListApp mApp;
    private ObjectListAdapter mObjectListAdapter;
    private AsyncTask<String, Void, List<Object>> mGetObjectsAsyncTask;
    private ListView mObjectListView;
    private View mContentLoadingView;
    private boolean mContentLoaded = false;
    private OkCancellableCall mGetObjectsAsyncCall;

    public ObjectListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        mApp = (ShoppingListApp) context.getApplicationContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_objects, container, false);
        mObjectListView = (ListView) layout.findViewById(R.id.object_list);
        mContentLoadingView = layout.findViewById(R.id.content_loading);
        showLoadingIndicator();
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //getLoaderManager().initLoader(OBJECT_LOADER_ID, null, this); //:) :(
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        //startGetObjectsAsyncTask(); //:(
        startGetObjectsAsyncCall(); //:)
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        //ensureGetObjectsAsyncTaskCancelled();
        ensureGetObjectsAsyncCallCancelled();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    private void startGetOnjectsAsyncTask() {
        if (mContentLoaded) {
            Log.d(TAG, "startGetObjectsAsyncTask - content already loaded, return");
            return;
        }
        mGetObjectsAsyncTask = new OkAsyncTask<String, Void, List<Object>>() {

            @Override
            protected void onPreExecute() {
                showLoadingIndicator();
                Log.d(TAG, "GetObjectsAsyncTask - showLoadingIndicator");
            }

            @Override
            protected List<Object> tryInBackground(String... params) throws Exception {
                Log.d(TAG, "GetObjectsAsyncTask - tryInBackground");
                return mApp.getObjectManager().getObjects();
            }

            @Override
            protected void onPostExecute(List<Object> objects) {
                Log.d(TAG, "GetObjectsAsyncTask - onPostExecute");
                if (backgroundException != null) {
                    Log.e(TAG, "Get objects failed");
                    showError(backgroundException);
                } else {
                    showContent(objects);
                }
            }
        }.execute();
    }

    private void ensureGetObjectsAsyncTaskCancelled() {
        if (mGetObjectsAsyncTask != null && !mGetObjectsAsyncTask.isCancelled()) {
            Log.d(TAG, "ensureGetObjectsAsyncTaskCancelled - cancelling the task");
            mGetObjectsAsyncTask.cancel(true);
        } else {
            Log.d(TAG, "ensureGetObjectsAsyncTaskCancelled - task already completed or cancelled");
        }
    }

    @Override
    public Loader<List<Object>> onCreateLoader(int id, Bundle args) {
        showLoadingIndicator();
        return mApp.getObjectManager().getObjectLoader();
    }

    @Override
    public void onLoadFinished(Loader<List<Object>> loader, List<Object> objects) {
        if (loader instanceof OkAsyncTaskLoader) {
            Exception loadingException = ((OkAsyncTaskLoader) loader).loadingException;
            if (loadingException != null) {
                Log.e(TAG, "Get object failed");
                showError(loadingException);
                return;
            }
            showContent(objects);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Object>> loader) {
        // not used
    }

    private void startGetObjectsAsyncCall() {
        if (mContentLoaded) {
            Log.d(TAG, "startGetObjectsAsyncCall - content already loaded, return");
            return;
        }
        mGetObjectsAsyncCall = mApp.getObjectManager().getObjectsAsync(
                new OnSuccessListener<List<Object>>() {
                    @Override
                    public void onSuccess(final List<Object> objects) {
                        Log.d(TAG, "startGetObjectsAsyncCall - success");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showContent(objects);
                            }
                        });
                    }
                }, new OnErrorListener() {
                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "startGetNotesAsyncCall - error");
                        showError(e);
                    }
                }
        );
    }

    private void ensureGetObjectsAsyncCallCancelled() {
        if (mGetObjectsAsyncCall != null) {
            Log.d(TAG, "ensureGetObjectsAsyncCallCancelled - cancelling the task");
            mGetObjectsAsyncCall.cancel();
        }
    }


    private void showError(Exception e) {
        Log.e(TAG, "showError", e);
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage(e.getMessage())
                .setCancelable(true)
                .create()
                .show();
    }

    private void showLoadingIndicator() {
        Log.d(TAG, "showLoadingIndicator");
        mObjectListView.setVisibility(View.GONE);
        mContentLoadingView.setVisibility(View.VISIBLE);
    }

    private void showContent(final List<Object> objects) {
        Log.d(TAG, "showContent");
        mObjectListAdapter = new ObjectListAdapter(this.getContext(), objects);
        mObjectListView.setAdapter(mObjectListAdapter);
        mContentLoadingView.setVisibility(View.GONE);
        mObjectListView.setVisibility(View.VISIBLE);
    }

}
