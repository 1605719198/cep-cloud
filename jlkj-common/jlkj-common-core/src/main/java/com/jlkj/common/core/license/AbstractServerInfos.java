package com.jlkj.common.core.license;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 111191
 * @version 1.0
 * @className AbstractServerInfos
 * @description 获取服务器硬件信息
 * @date 2023年4月2日, 0002 11
 */
@Slf4j
public abstract class AbstractServerInfos {

    public LicenseCheckModel getServerInfos() {
        LicenseCheckModel result = new LicenseCheckModel();
        try {
            result.setIpAddress(this.getIpAddress());
        } catch (Exception e) {
            log.error("获取服务器硬件信息失败", e);
        }
        return result;
    }

    /**
     * 获取IP 地址
     *
     * @return List<String>
     * @author 111191
     * @Date 下午 12:35:22 2023年4月2日, 0002
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * 获取Mac地址
     *
     * @return List<String>
     * @author 111191
     * @Date 下午 12:35:05 2023年4月2日, 0002
     */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * 获取CPU序列号
     *
     * @return String
     * @author 111191
     * @Date 下午 12:34:35 2023年4月2日, 0002
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * 获取主板序列号
     *
     * @return String
     * @author 111191
     * @Date 下午 12:34:51 2023年4月2日, 0002
     */
    protected abstract String getMainBoardSerial() throws Exception;

    /**
     * 获取服务器所有符合条件的inetAddress
     *
     * @return List<InetAddress>
     * @author 111191
     * @Date 上午 11:31:08 2023年4月2日, 0002
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = networkInterfaces.nextElement();
            for (Enumeration<InetAddress> inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = inetAddresses.nextElement();
                if (!inetAddr.isLoopbackAddress() && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()) {
                    result.add(inetAddr);
                }
            }
        }
        return result;
    }

    /**
     * 获取某个网络接口的 Mac地址
     *
     * @param inetAddress
     * @return String
     * @author 111191
     * @Date 下午 12:40:32 2023年4月2日, 0002
     */
    protected String getMacByInetAddress(InetAddress inetAddress) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    stringBuffer.append("-");
                }
                String temp = Integer.toHexString(mac[i] & 0xff);
                if (temp.length() == 1) {
                    stringBuffer.append("0").append(temp);
                } else {
                    stringBuffer.append(temp);
                }
            }
            return stringBuffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
