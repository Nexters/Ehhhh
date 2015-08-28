package com.teamnexters.ehhhh.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by cha on 2015-08-28.
 */
public class GNetworkInfo {

    //Mobile Phone
    public static boolean IsWifiAvailable(Context context) {
        ConnectivityManager m_NetConnectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Mobile 연결자 기본
        NetworkInfo ni = m_NetConnectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = ni.isConnected();

        // WiFi 관련 정보를 얻습니다.
        ni = m_NetConnectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = ni.isConnected();

        if (isWifiConn || isMobileConn) {
            return true;
        } else {
            return false;
        }
    }
}
