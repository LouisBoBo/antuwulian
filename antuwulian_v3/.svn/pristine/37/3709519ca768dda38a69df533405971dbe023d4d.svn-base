package com.slxk.gpsantu.mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class MMKVUtils {

    private static final Map<String, MMKVUtils> SP_UTILS_MAP = new ConcurrentHashMap<>();
    private MMKV kv;

    /**
     * Return the single {@link MMKVUtils} instance
     *
     * @return the single {@link MMKVUtils} instance
     */
    public static MMKVUtils getInstance() {
        return getInstance("", Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link MMKVUtils} instance
     *
     * @param mode Operating mode.
     * @return the single {@link MMKVUtils} instance
     */
    public static MMKVUtils getInstance(final int mode) {
        return getInstance("", mode);
    }

    /**
     * Return the single {@link MMKVUtils} instance
     *
     * @param spName The name of kv.
     * @return the single {@link MMKVUtils} instance
     */
    public static MMKVUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link MMKVUtils} instance
     *
     * @param spName The name of kv.
     * @param mode   Operating mode.
     * @return the single {@link MMKVUtils} instance
     */
    public static MMKVUtils getInstance(String spName, final int mode) {
        if (isSpace(spName)) spName = "MMKVUtils";
        MMKVUtils MMKVUtils = SP_UTILS_MAP.get(spName);
        if (MMKVUtils == null) {
            MMKVUtils = new MMKVUtils(spName, mode);
            SP_UTILS_MAP.put(spName, MMKVUtils);
        }
        return MMKVUtils;
    }


    private MMKVUtils(final String spName, final int mode) {
        kv = MMKV.mmkvWithID(spName, mode);
    }

    /**
     * Put the string value in kv.
     *
     * @param key   The key of kv.
     * @param value The value of kv.
     */
    public void put(@NonNull final String key, final String value) {
        kv.encode(key, value);
    }

    /**
     * Return the string value in kv.
     *
     * @param key The key of kv.
     * @return the string value if kv exists or {@code ""} otherwise
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    /**
     * Return the string value in kv.
     *
     * @param key          The key of kv.
     * @param defaultValue The default value if the kv doesn't exist.
     * @return the string value if kv exists or {@code defaultValue} otherwise
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        return kv.getString(key, defaultValue);
    }

    /**
     * Put the int value in kv.
     *
     * @param key   The key of kv.
     * @param value The value of kv.
     */
    public void put(@NonNull final String key, final int value) {
        kv.encode(key, value);
    }

    /**
     * Return the int value in kv.
     *
     * @param key The key of kv.
     * @return the int value if kv exists or {@code defaultValue} otherwise
     */
    public int getInt(@NonNull final String key) {
        return kv.getInt(key, -1);
    }

    /**
     * Return the int value in kv.
     *
     * @param key          The key of kv.
     * @param defaultValue The default value if the kv doesn't exist.
     * @return the int value if kv exists or {@code defaultValue} otherwise
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return kv.getInt(key, defaultValue);
    }

    /**
     * Put the long value in kv.
     *
     * @param key   The key of kv.
     * @param value The value of kv.
     */
    public void put(@NonNull final String key, final long value) {
        kv.encode(key, value);
    }

    /**
     * Return the long value in kv.
     *
     * @param key The key of kv.
     * @return the long value if kv exists or {@code -1} otherwise
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * Return the long value in kv.
     *
     * @param key          The key of kv.
     * @param defaultValue The default value if the kv doesn't exist.
     * @return the long value if kv exists or {@code defaultValue} otherwise
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return kv.getLong(key, defaultValue);
    }

    /**
     * Put the float value in kv.
     *
     * @param key   The key of kv.
     * @param value The value of kv.
     */
    public void put(@NonNull final String key, final float value) {
        kv.encode(key, value);
    }


    /**
     * Return the float value in kv.
     *
     * @param key The key of kv.
     * @return the float value if kv exists or {@code -1f} otherwise
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * Return the float value in kv.
     *
     * @param key          The key of kv.
     * @param defaultValue The default value if the kv doesn't exist.
     * @return the float value if kv exists or {@code defaultValue} otherwise
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return kv.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in kv.
     *
     * @param key   The key of kv.
     * @param value The value of kv.
     */
    public void put(@NonNull final String key, final boolean value) {
        kv.encode(key, value);
    }


    /**
     * Return the boolean value in kv.
     *
     * @param key The key of kv.
     * @return the boolean value if kv exists or {@code false} otherwise
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * Return the boolean value in kv.
     *
     * @param key          The key of kv.
     * @param defaultValue The default value if the kv doesn't exist.
     * @return the boolean value if kv exists or {@code defaultValue} otherwise
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return kv.getBoolean(key, defaultValue);
    }

    /**
     * Put the set of string value in kv.
     *
     * @param key   The key of kv.
     * @param value The value of kv.
     */
    public void put(@NonNull final String key, final Set<String> value) {
        kv.encode(key, value);
    }

    /**
     * Return the set of string value in kv.
     *
     * @param key The key of kv.
     * @return the set of string value if kv exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * Return the set of string value in kv.
     *
     * @param key          The key of kv.
     * @param defaultValue The default value if the kv doesn't exist.
     * @return the set of string value if kv exists or {@code defaultValue} otherwise
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    final Set<String> defaultValue) {
        return kv.getStringSet(key, defaultValue);
    }

    /**
     * Return whether the kv contains the preference.
     *
     * @param key The key of kv.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean contains(@NonNull final String key) {
        return kv.contains(key);
    }

    /**
     * Remove the preference in kv.
     *
     * @param key The key of kv.
     */
    public void remove(@NonNull final String key) {
        kv.remove(key);
    }

    /**
     * Remove all preferences in kv.
     */
    public void clear() {
        kv.clear();
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Change SP to MMKV
     *
     * @param mContext
     */
    public void changeSPToMMKV(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        int size = kv.importFromSharedPreferences(sp);
        if (size > 0) sp.edit().clear().apply();
    }
}
