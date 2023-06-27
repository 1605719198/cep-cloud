package com.jlkj.system.license;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 111191
 * @version 1.0
 * @className LicenseCheckModel
 * @description TODO
 * @date 2023年4月2日, 0002 10
 */
@Data
public class LicenseCheckModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 可被允许的 IP地址
     *
     * @author 111191
     * @Date 上午 11:00:24 2023年4月2日, 0002
     * @param null
     */
    private List<String> ipAddress;
    /**
     * 可被允许的MAC地址
     *
     * @author 111191
     * @Date 上午 11:01:33 2023年4月2日, 0002
     * @param null
     */
    private List<String> macAddress;
    /**
     * 可被允许的CPU 序列号
     *
     * @author 111191
     * @Date 上午 11:01:38 2023年4月2日, 0002
     * @param null
     */
    private String cpuSerial;
    /**
     * 可被允许的主板序列号
     *
     * @author 111191
     * @Date 上午 11:02:29 2023年4月2日, 0002
     * @param null
     */
    private String mainBoardSerial;
    /**
     * 服务器操作系统类型 （windows\linux\mac os)
     *
     * @author 111191
     * @Date 上午 10:03:06 2023年4月4日, 0004
     * @param null
     */
    private String osName;
    /**
     * 机器码
     *
     * @author 111191
     * @Date 下午 03:38:19 2023年4月4日, 0004
     * @param null
     */
    private String machineCode;

    private Integer userNum;

    private Map<String, Object> params;
}
