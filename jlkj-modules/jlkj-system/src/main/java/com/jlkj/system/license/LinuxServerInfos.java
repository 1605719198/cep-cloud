package com.jlkj.system.license;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 111191
 * @version 1.0
 * @className LinuxServerInfos
 * @description TODO
 * @date 2023年4月2日, 0002 12
 */
public class LinuxServerInfos extends AbstractServerInfos {

    /**
     * 获取Linux下的IP地址
     *
     * @return List<String>
     * @author 111191
     * @Date 下午 12:49:28 2023年4月2日, 0002
     */
    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;
        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (inetAddresses != null && inetAddresses.size() > 0) {
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * 获取Linux下的Mac地址
     *
     * @return List<String>
     * @author 111191
     * @Date 下午 12:49:57 2023年4月2日, 0002
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
     * 获取CPU序列号
     *
     * @return String
     * @author 111191
     * @Date 下午 12:51:25 2023年4月2日, 0002
     */
    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用dmidecode命令获取CPU序列号
        String[] shell = {"/bin/bash", "-c", "dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        return getString(serialNumber, shell);
    }

    /**
     * 获取主板序列号
     *
     * @return String
     * @author 111191
     * @Date 下午 12:52:07 2023年4月2日, 0002
     */
    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用dmidecode命令获取主板序列号
        String[] shell = {"/bin/bash", "-c", "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        return getString(serialNumber, shell);
    }

    /**
     * 取得序列号公用方法（抽取代码）
     *
     * @param serialNumber
     * @param shell
     * @return String
     * @author 111191
     * @Date 下午 12:53:40 2023年4月2日, 0002
     */
    private String getString(String serialNumber, String[] shell) throws IOException {
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine().trim();
        if (StringUtils.isNotBlank(line)) {
            serialNumber = line;
        }
        reader.close();
        return serialNumber;
    }

}
