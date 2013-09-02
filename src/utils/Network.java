
package utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

@SuppressWarnings("deprecation")
public class Network {

    public static String getCurrentNetworkMask(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();

        int netmask = dhcpInfo.netmask;
        String mask = String.valueOf(Math.round(Math.log(netmask) / Math.log(2.0)));

//        int gateway = dhcpInfo.gateway;
        int gateway = dhcpInfo.ipAddress;
        byte[] ipbytes = new byte[4];
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            ipbytes[0] = (byte) (gateway & 0xFF);
            ipbytes[1] = (byte) (0xFF & gateway >> 8);
            ipbytes[2] = (byte) (0xFF & gateway >> 16);
            ipbytes[3] = (byte) (0xFF & gateway >> 24);
        } else {
            ipbytes[0] = (byte) (0xFF & gateway >> 24);
            ipbytes[1] = (byte) (0xFF & gateway >> 16);
            ipbytes[2] = (byte) (0xFF & gateway >> 8);
            ipbytes[3] = (byte) (gateway & 0xFF);
        }

        try {
            return InetAddress.getByAddress(ipbytes).getHostAddress();// + "/" + mask;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
