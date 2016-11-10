package com.monika.shoppinglist.util;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by Mónika on 2016. 11. 10..
 */


public interface ResourceReader<E> {
    E read(JsonReader reader) throws IOException;
}
