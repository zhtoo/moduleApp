package com.zht.modulelibrary.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.IpConfiguration;
import android.net.ProxyInfo;
import android.net.StaticIpConfiguration;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.listener.PermissionCallBack;
import com.zht.modulelibrary.databinding.ActivityWifiBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2023/3/2 17:25
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.WIFI_ACTIVITY)
public class WifiActivity extends BaseViewBindingActivity<ActivityWifiBinding> {

    private final static String TAG = "WiFi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(
                new String[]{
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },

                new PermissionCallBack() {
                    @Override
                    public void granted() {
                        testWifi();
                    }

                    @Override
                    public void denied() {
                        Toast.makeText(WifiActivity.this, "无权限访问相应文件.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void testWifi() {
        getWifiInfo();
        List<String> wifiInfoList = getAroundWifiDeviceInfo();
        for (String info : wifiInfoList) {
            Log.e(TAG, info);
        }
    }

    public void getWifiInfo() {
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        int Ip = mWifiInfo.getIpAddress();
        String strIp = "" + (Ip & 0xFF) + "." + ((Ip >> 8) & 0xFF) + "." + ((Ip >> 16) & 0xFF) + "." + ((Ip >> 24) & 0xFF);
        Log.e(TAG, "BSSID : " + mWifiInfo.getBSSID());
        Log.e(TAG, "SSID : " + mWifiInfo.getSSID());
        Log.e(TAG, "nIpAddress : " + strIp);
        Log.e(TAG, "MacAddress : " + mWifiInfo.getMacAddress());
        Log.e(TAG, "NetworkId : " + mWifiInfo.getNetworkId());
        Log.e(TAG, "LinkSpeed : " + mWifiInfo.getLinkSpeed() + "Mbps");
        Log.e(TAG, "Rssi : " + mWifiInfo.getRssi());
        Log.e(TAG, "SupplicantState : " + mWifiInfo.getSupplicantState());
        Log.e(TAG, "WifiInfo : " + mWifiInfo);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfig:configuredNetworks) {
            if(wifiConfig !=null && TextUtils.equals(wifiConfig.SSID,mWifiInfo.getSSID())){
                ProxyInfo proxyInfo = ProxyInfo.buildDirectProxy("192.168.0.1", 8888);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    wifiConfig.setHttpProxy(proxyInfo);
                    mWifiManager.updateNetwork(wifiConfig);
                }
            }
        }

//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//
//            WifiNetworkSuggestion.Builder builder = new WifiNetworkSuggestion.Builder();
//            WifiNetworkSuggestion networkSuggestion = builder
//                    .setSsid(mWifiInfo.getSSID())
//                    .build();
//
//            mWifiManager.addNetworkSuggestions(Arrays.asList(networkSuggestion));
////            WifiNetworkSuggestion.Builder()
////                    .setSsid(ssid)
////                    .setWpa2Passphrase(password)
////                    .setIsAppInteractionRequired(false) // Optional (Needs location permission)
////                    .build()
//
//        }else{
//
//
//        }
    }

    //获取附近wifi信号
    public List<String> getAroundWifiDeviceInfo() {

        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        List<ScanResult> newScanResultList = mWifiManager.getScanResults();//搜索到的设备列表
//        List<ScanResult> newScanResultList = new ArrayList<>();
//        for (ScanResult scanResult : scanResults) {
//            int position = getItemPosition(newScanResultList, scanResult);
//            if (position != -1) {
//                if (newScanResultList.get(position).level < scanResult.level) {
//                    newScanResultList.remove(position);
//                    newScanResultList.add(position, scanResult);
//                }
//            } else {
//                newScanResultList.add(scanResult);
//            }
//        }
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < newScanResultList.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("设备名(SSID) ->" + newScanResultList.get(i).SSID + "\n");
            stringBuilder.append("信号强度 ->" + newScanResultList.get(i).level + "\n");
            stringBuilder.append("BSSID ->" + newScanResultList.get(i).BSSID + "\n");
            stringBuilder.append("level ->" + newScanResultList.get(i).level + "\n");
            stringBuilder.append("采集时间戳 ->" + System.currentTimeMillis() + "\n");
            stringBuilder.append("rssi ->" + (mWifiInfo != null && (mWifiInfo.getSSID().replace("\"", "")).equals(newScanResultList.get(i).SSID) ? mWifiInfo.getRssi() : null) + "\n");
//是否为连接信号(1连接，默认为null)
            stringBuilder.append("是否为连接信号 ->" + (mWifiInfo != null && (mWifiInfo.getSSID().replace("\"", "")).equals(newScanResultList.get(i).SSID) ? 1 : null) + "\n");
            stringBuilder.append("信道 - >" + getCurrentChannel(mWifiManager) + "\n");
//1 为2.4g 2 为5g
            stringBuilder.append("频段 ->" + is24GOr5GHz(newScanResultList.get(i).frequency));
            stringList.add(stringBuilder.toString());
        }
        return stringList;
    }

    /**
     * 获取频段
     */
    public static String is24GOr5GHz(int freq) {
        if (freq > 2400 && freq < 2500) {
            return "1";
        } else if (freq > 4900 && freq < 5900) {
            return "2";
        } else {
            return "无法判断";
        }
    }

    /**
     * 返回item在list中的坐标
     */
    private int getItemPosition(List<ScanResult> list, ScanResult item) {
        for (int i = 0; i < list.size(); i++) {
            if (item.SSID.equals(list.get(i).SSID)) {
                return i;
            }
        }
        return -1;
    }

    public int getCurrentChannel(WifiManager wifiManager) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();// 当前wifi连接信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return -1;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult result : scanResults) {
            if (result.BSSID.equalsIgnoreCase(wifiInfo.getBSSID())
                    && result.SSID.equalsIgnoreCase(wifiInfo.getSSID()
                    .substring(1, wifiInfo.getSSID().length() - 1))) {
                return getChannelByFrequency(result.frequency);
            }
        }
        return -1;
    }

    /**
     * 根据频率获得信道
     *
     * @param frequency
     * @return
     */
    public static int getChannelByFrequency(int frequency) {
        int channel = -1;
        switch (frequency) {
            case 2412:
                channel = 1;
                break;
            case 2417:
                channel = 2;
                break;
            case 2422:
                channel = 3;
                break;
            case 2427:
                channel = 4;
                break;
            case 2432:
                channel = 5;
                break;
            case 2437:
                channel = 6;
                break;
            case 2442:
                channel = 7;
                break;
            case 2447:
                channel = 8;
                break;
            case 2452:
                channel = 9;
                break;
            case 2457:
                channel = 10;
                break;
            case 2462:
                channel = 11;
                break;
            case 2467:
                channel = 12;
                break;
            case 2472:
                channel = 13;
                break;
            case 2484:
                channel = 14;
                break;
            case 5745:
                channel = 149;
                break;
            case 5765:
                channel = 153;
                break;
            case 5785:
                channel = 157;
                break;
            case 5805:
                channel = 161;
                break;
            case 5825:
                channel = 165;
                break;
        }
        return channel;
    }




    // 添加一个网络并连接
    public void connectWifi(WifiConfiguration wifiConfig) {
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {

        }else  if (Build.VERSION.SDK_INT ==  Build.VERSION_CODES.P)  {

        }else {

        }


    }


    public void connectWifiBeforeP(WifiConfiguration wifiConfig) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            return;
        }
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wcgID = mWifiManager.addNetwork(wifiConfig);
        boolean b =  mWifiManager.enableNetwork(wcgID, true);
    }

    public void connectWifiOnP(WifiConfiguration wifiConfig) {
        if(Build.VERSION.SDK_INT != Build.VERSION_CODES.P){
            return;
        }
        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        mWifiManager.connect(wifiConfig, null);
    }

    public void connectWifiAfterP(WifiConfiguration wifiConfig) {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
            return;
        }
    }

    /**
     *
     * @param wifiName  Wifi名称
     * @param password  Wifi密码
     * @param proxyIP   代理ip
     * @return
     */
    public WifiConfiguration createWifiConfig(String wifiName,String password,String proxyIP){
        WifiConfiguration config = new WifiConfiguration();
        //wifi名称
        config.SSID = wifiName;//wifi名称,一般通过扫描获取
        //config.networkId = "XXXId";//wifiId，非扫描的情况，比如添加wifi的情况，可以不写

        //wifi密码
        config.preSharedKey = "\""+password+"\"";//wifi加密形式，不加密的wifi，无需设置密码
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK); //加密形式，XXX为NONE/WPA_PSK/WPA_EAP
        //config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.NONE); //验证算法，XXX为OPEN/SHARED,NONE的情况无需设置


//静态ip相关数据和代理相关数据

//        IpAssignment mIpAssignment = IpAssignment.DHCP;//静态配置，静态默认是：DHCP，如果是静态ip：IpAssignment.STATIC；
//        StaticIpConfiguration mStaticIpConfiguration = null;//静态ip，才有必要设置，否则传入null即可
//        ProxySettings mProxySettings = ProxySettings.UNASSIGNED; //代理情况，是否静态ip
//        ProxyInfo mHttpProxy = null; //代理的参数数据保存对象，无代理的情况可不用实例化，传入null即可



        //设置静态ip相关数据
//        mStaticIpConfiguration = new StaticIpConfiguration();
//        Inet4Address inetAddr = (Inet4Address) NetworkUtils.numericToInetAddress("ipString")；//ipString为静态ip地址，NetworkUtils是系统net的工具类
//        mStaticIpConfiguration.ipAddress = new LinkAddress(inetAddr, networkPrefixLength); //IP前缀长度，networkPrefixLength是0-32的数值，一般设置24
//        InetAddress gatewayAddr = (Inet4Address) NetworkUtils.numericToInetAddress("gatewayIp")；//gatewayIp为网关地址
//        mStaticIpConfiguration.gateway = gatewayAddr;
//        Inet4Address dnsAddr = (Inet4Address) NetworkUtils.numericToInetAddress("dnsString")；//dns为域名地址
//        mStaticIpConfiguration.dnsServers.add(dnsAddr);//域名可以添加多个，一般手机是显示可添加两个

        //代理设置，分为NONE无/MANUAL手动/PAC自动
//        mProxySettings = ProxySettings.STATIC;//无：ProxySettings.NONE; 自动：ProxySettings.PAC
        //手动代理最为常见，需要填入三个数据：host为主机名称，port为服务器端口，exclusionList为绕过的地址（多个的情况可使用，号分隔）
//        mHttpProxy = new ProxyInfo(host, port, exclusionList);

        //自动代理用得不多，只需输入一个PAC网址即可：
        //Uri uri = Uri.parse("pac_ip");
        //mHttpProxy = new ProxyInfo(uri);

//最后把静态ip和代理相关数据设置到IpConfiguration对象中即可
//        IpConfiguration ipConfiguration = new IpConfiguration(mIpAssignment,mProxySettings,mStaticIpConfiguration,mHttpProxy);
//
//            config.setIpConfiguration(ipConfiguration);


        return config;
    }

}



