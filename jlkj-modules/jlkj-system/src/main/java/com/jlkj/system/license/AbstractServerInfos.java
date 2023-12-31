package com.jlkj.system.license;

import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author 111191
 * @version 1.0
 * @className AbstractServerInfos
 * @description 获取服务器硬件信息
 * @date 2023年4月2日, 0002 11
 */
@Slf4j
public abstract class AbstractServerInfos {

    private static final int SPLITLENGTH = 4;
    public static final String WINDOWS = "windows";
    public static final String CENT = "cent";
    public static final String MAC = "mac";
    public static final String LINUX = "linux";
    public static final String OS_NAME = "os.name";
/**
 * 参数检查项
 * @author 111191
 * @Date 上午 10:00:46 2023年4月5日, 0005
 * @return LicenseCheckModel
 */
    public LicenseCheckModel getServerInfos() {
        LicenseCheckModel result = new LicenseCheckModel();
        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
            result.setMachineCode(this.getMachineCode());
            String osName = System.getProperty(OS_NAME).toLowerCase();
            if (osName.startsWith(WINDOWS)) {
                result.setOsName(WINDOWS);
            }else if(osName.startsWith(CENT)){
                result.setOsName(CENT);
            }else if(osName.startsWith(MAC)){
                result.setOsName(MAC);
            }else {
                result.setOsName(LINUX);
            }
        } catch (Exception e) {
            log.error("获取服务器硬件信息失败", e);
        }
        return result;
    }

    /**
     * 获取IP 地址
     * @return
     * @throws Exception
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * 获取Mac地址
     * @return
     * @throws Exception
     */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * 获取CPU序列号
     * @return
     * @throws Exception
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * 获取主板序列号
     * @return
     * @throws Exception
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
        // 遍历所有的网络接口
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration<InetAddress> inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = inetAddresses.nextElement();
                // 排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
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

    /**
     * 取得机器码
     *
     * @return String
     * @author 111191
     * @Date 下午 03:22:03 2023年4月4日, 0004
     */
    protected String getMachineCode() throws Exception {
        Set<String> result = new HashSet<>();
        String ip = this.getIpAddress().toString();
        result.add(ip);
        String mac = this.getMacAddress().toString();
        result.add(mac);
        String cpu = this.getCPUSerial();
        result.add(cpu);
        Properties props = System.getProperties();
        String javaVersion = props.getProperty("java.version");
        result.add(javaVersion);
        String javaVMVersion = props.getProperty("java.vm.version");
        result.add(javaVMVersion);
        String osVersion = props.getProperty("os.name");
        result.add(osVersion);
        String code = MD5.create().digestHex(result.toString(), Charset.forName("utf-8"));
        return getSplitString(code, "-", 4);

    }

    /**
     * 使用‘-’ 分隔字符串
     *
     * @param str 待分隔字符串
     * @return String
     * @author 111191
     * @Date 下午 03:17:08 2023年4月4日, 0004
     */
    private static String getSplitString(String str) {
        return getSplitString(str, "-", SPLITLENGTH);
    }

    /**
     * 分隔字符串
     *
     * @param str    字符串
     * @param split  分隔符
     * @param length 分隔间隔
     * @return String
     * @author 111191
     * @Date 下午 03:17:11 2023年4月4日, 0004
     */
    private static String getSplitString(String str, String split, int length) {
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i % length == 0 && i > 0) {
                temp.append(split);
            }
            temp.append(str.charAt(i));
        }
        String[] attrs = temp.toString().split(split);
        StringBuilder finalMachineCode = new StringBuilder();
        for (String attr : attrs) {
            if (attr.length() == length) {
                finalMachineCode.append(attr).append(split);
            }
        }
        String result = finalMachineCode.toString().substring(0, finalMachineCode.toString().length() - 1);
        return result;
    }

}
