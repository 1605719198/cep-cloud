package com.jlkj.system.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.system.license.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 111191
 * @version 1.0
 * @className LicenseCreatorController
 * @description 用于生成证书文件，不能放在给客户部署的代码里
 * @date 2023年4月2日, 0002 13
 */
@Tag(name = "版本授权")
@RestController
@RequestMapping("/license")
public class LicenseCreatorController extends BaseController {
    /**
     * 证书生成路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 获取服务器硬件信息
     *
     * @param osName 操作系统类型，如果为空则自动判断
     * @return AjaxResult
     */
    @RequiresPermissions("system:license:read")
    @RequestMapping(value = "/getServerInfos", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public AjaxResult getServerInfos(@RequestParam(value = "osName", required = false) String osName) {
        //操作系统类型
        if (StringUtils.isBlank(osName)) {
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();
        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        } else {//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }
        return success(abstractServerInfos.getServerInfos());
    }

    /**
     * 生成证书
     *
     * @param param 生成证书需要的参数，
     *              如：{"subject":"ccx-models","privateAlias":"privateKey",
     *              "keyPass":"5T7Zz5Y0dJFcqTxvzkH5LDGJJSGMzQ",
     *              "storePass":"3538cef8e7",
     *              "licensePath":"C:/Users/zifangsky/Desktop/license.lic",
     *              "privateKeysStorePath":"C:/Users/zifangsky/Desktop/privateKeys.keystore",
     *              "issuedTime":"2018-04-26 14:48:12",
     *              "expiryTime":"2018-12-31 00:00:00",
     *              "consumerType":"User",
     *              "consumerAmount":1,
     *              "description":"这是证书描述信息",
     *              "licenseCheckModel":{"ipAddress":["192.168.245.1","10.0.5.22"],
     *              "macAddress":["00-50-56-C0-00-01","50-7B-9D-F9-18-41"],
     *              "cpuSerial":"BFEBFBFF000406E3",
     *              "mainBoardSerial":"L1HF65E00X9"}}
     * @return AjaxResult
     */
    @Log(title = "证书文件生成", businessType = BusinessType.GRANT)
    @PostMapping("/generateLicense")
    public Map<String, Object> generateLicense(@RequestBody LicenseCreatorParam param) {
        Map<String, Object> resultMap = new HashMap<>(2);
        if(StringUtils.isBlank(param.getLicensePath())) {
            param.setLicensePath(licensePath);
        }
        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if (result) {
            resultMap.put("result","ok");
            resultMap.put("msg",param);
        } else {
            resultMap.put("result","error");
            resultMap.put("msg","证书文件生成失败");
        }
        return resultMap;
    }
}
