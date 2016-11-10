package com.monika.shoppinglist.util;

import android.util.JsonWriter;

import java.io.IOException;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public interface ResourceWriter<E> {
    void write(E e, JsonWriter writer) throws IOException;
}