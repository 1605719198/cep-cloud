package com.jlkj.system.license;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 111191
 * @version 1.0
 * @className LicenseCreatorParam
 * @description TODO
 * @date 2023年4月2日, 0002 11
 */
@Data
public class LicenseCreatorParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 证书 subject
     *
     * @author 111191
     * @Date 上午 11:06:13 2023年4月2日, 0002
     * @param null
     */
    private String subject;
    /**
     * 密钥（私钥）别称
     *
     * @author 111191
     * @Date 上午 11:06:31 2023年4月2日, 0002
     * @param null
     */
    private String privateAlias;
    /**
     * 密钥（私钥）密码（需妥善保管，不能让使用者知道）
     *
     * @author 111191
     * @Date 上午 11:06:53 2023年4月2日, 0002
     * @param null
     */
    private String keyPass;
    /**
     * 访问密钥库（私钥）的密码
     *
     * @author 111191
     * @Date 上午 11:07:36 2023年4月2日, 0002
     * @param null
     */
    private String storePass;
    /**
     * 证书生成路径
     *
     * @author 111191
     * @Date 上午 11:09:21 2023年4月2日, 0002
     * @param null
     */
    private String licensePath;
    /**
     * 密钥（私钥）存储路径
     *
     * @author 111191
     * @Date 上午 11:09:36 2023年4月2日, 0002
     * @param null
     */
    private String privateKeysStorePath;
    /**
     * 证书生效时间
     *
     * @author 111191
     * @Date 上午 11:09:59 2023年4月2日, 0002
     * @param null
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();
    /**
     * 证书失效时间
     *
     * @author 111191
     * @Date 上午 11:09:59 2023年4月2日, 0002
     * @param null
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime = new Date();
    /**
     * 用户类型
     *
     * @author 111191
     * @Date 上午 11:13:01 2023年4月2日, 0002
     * @param null
     */
    private String consumerType = "user";
    /**
     * 用户数量
     *
     * @author 111191
     * @Date 上午 11:13:12 2023年4月2日, 0002
     * @param null
     */
    private Integer consumerAmount = 1;
    /**
     * 描述信息
     *
     * @author 111191
     * @Date 上午 11:14:12 2023年4月2日, 0002
     * @param null
     */
    private String description = "";
    /**
     * 额外的License检验参数
     *
     * @author 111191
     * @Date 上午 11:14:24 2023年4月2日, 0002
     * @param null
     */
    private LicenseCheckModel licenseCheckModel;
}
