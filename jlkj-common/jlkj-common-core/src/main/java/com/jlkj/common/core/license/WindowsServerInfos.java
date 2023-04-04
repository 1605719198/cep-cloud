package com.jlkj.common.core.license;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 用于获取客户Windows服务器的基本信息
 *
 * @author 111191
 * @version 1.0
 * @className WindowsServerInfos
 * @description 用于获取客户Windows服务器的基本信息
 * @date 2023年4月2日, 0002 12
 */
public class WindowsServerInfos extends AbstractServerInfos {
    /**
     * 获取所有网络接口 IP 地址
     *
     * @return List<String>
     * @author 111191
     * @Date 下午 12:56:33 2023年4月2日, 0002
     */
    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> myList = null;

        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if (inetAddresses != null && inetAddresses.size() > 0) {
            myList = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }

        return myList;
    }

    /**
     * 获取所有网络接口 Mac 地址
     *
     * @return List<String>
     * @author 111191
     * @Date 下午 12:56:28 2023年4月2日, 0002
     */
    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;
        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (inetAddresses != null && inetAddresses.size() > 0) {
            //2. 获取所有网络接口的Mac地址
            result = inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 使用WMIC获取CPU序列号
     *
     * @return String
     * @author 111191
     * @Date 下午 12:56:25 2023年4月2日, 0002
     */
    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用WMIC获取CPU序列号
        Process process = Runtime.getRuntime().exec("wmic cpu get processorid");
        return getString(serialNumber, process);
    }

    /**
     * 使用WMIC获取主板序列号
     *
     * @return String
     * @author 111191
     * @Date 下午 12:56:21 2023年4月2日, 0002
     */
    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用WMIC获取主板序列号
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        return getString(serialNumber, process);
    }

    /**
     * 公用获取服务器序列号方法（代码抽取）
     *
     * @param serialNumber
     * @param process
     * @return String
     * @author 111191
     * @Date 下午 12:56:15 2023年4月2日, 0002
     */
    private String getString(String serialNumber, Process process) throws IOException {
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());
        if (scanner.hasNext()) {
            scanner.next();
        }
        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }
        scanner.close();
        return serialNumber;
    }
}
