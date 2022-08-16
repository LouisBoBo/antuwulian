package com.slxk.gpsantu.mvp.utils;

import android.location.Address;
import android.location.Geocoder;

import com.slxk.gpsantu.app.MyApplication;

import java.util.List;
import java.util.Locale;

/**
 * 谷歌地图获取地址
 */
public class LocationGoogleAddress {

    // google解析地址
    private Geocoder geocoder;

    public LocationGoogleAddress() {
        geocoder = new Geocoder(MyApplication.getMyApp(), Locale.ENGLISH);
    }

    public String Parsed(double Latitude, double longitude) {
        String addressText = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(Latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address addressInfo = addresses.get(0);
                if (addressInfo.getMaxAddressLineIndex() >= 0) {
                    addressText += addressInfo.getAddressLine(0);
                }
                if (addressInfo.getMaxAddressLineIndex() >= 1) {
                    if (addressText.length() != 0) addressText += ",";
                    addressText += addressInfo.getAddressLine(1);
                }
                if (addressInfo.getMaxAddressLineIndex() >= 2) {
                    if (addressText.length() != 0) addressText += ",";
                    addressText += addressInfo.getAddressLine(2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressText;
    }
}
