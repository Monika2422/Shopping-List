package com.monika.shoppinglist.net;

import android.provider.ContactsContract;
import android.util.JsonReader;
import android.util.Log;

import com.monika.shoppinglist.content.Object;
import com.monika.shoppinglist.util.ResourceReader;

import java.io.IOException;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class ObjectReader implements ResourceReader<Object> {
    private static final String TAG = ObjectReader.class.getSimpleName();

    public static final String OBJECT_ID = "_id";
    public static final String OBJECT_NAME = "name";
    public static final int OBJECT_QUANT = 0;
    public static final String OBJECT_STATUS = "status";
    public static final String OBJECT_UPDATED = "updated";

    @Override
    public Object read(JsonReader reader) throws IOException {
        Object object = new Object();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(OBJECT_ID)) {
                object.setId(reader.nextString());
            } else if (name.equals(OBJECT_NAME)) {
                object.setName(reader.nextString());
            } else if (name.equals(OBJECT_STATUS)) {
                object.setStatus(Object.Status.valueOf(reader.nextString()));
            } else if (name.equals(OBJECT_UPDATED)) {
                object.setUpdated(reader.nextLong());
            } else {
                reader.skipValue();
                Log.w(TAG, String.format("Object property '%s' ignored", name));
            }
        }
        reader.endObject();
        return object;
    }
}
