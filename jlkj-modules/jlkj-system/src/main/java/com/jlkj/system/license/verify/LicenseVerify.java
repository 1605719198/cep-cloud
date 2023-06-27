package com.jlkj.system.license.verify;

import com.jlkj.common.core.constant.CacheConstants;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.redis.service.RedisService;
import com.jlkj.system.license.CustomKeyStoreParam;
import com.jlkj.system.license.LicenseCheckModel;
import com.jlkj.system.license.config.BeanUtils;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.prefs.Preferences;

/**
 * License校验类
 * @author jlkj
 */
@Slf4j
public class LicenseVerify {
    RedisService redisService = BeanUtils.getBean(RedisService.class);
    /**
     * 安装License证书
     */
    public synchronized LicenseContent install(LicenseVerifyParam param){
        LicenseContent result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //1. 安装证书
        try{
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam(param));
            licenseManager.uninstall();
            result = licenseManager.install(ResourceUtils.getFile(param.getLicensePath()));
            log.info("证书安装成功，证书有效期："+format.format(result.getNotBefore())+"-"+format.format(result.getNotAfter()));
        }catch (Exception e){
            log.error("证书安装失败，证书已经失效！",e);
        }
        return result;
    }

    /**
     * 校验License证书
     * @return boolean
     */
    public boolean verify(){
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //2. 校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();
            LicenseCheckModel checkModel = (LicenseCheckModel) licenseContent.getExtra();
            Collection<String> keys = redisService.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
            if (keys.size() > checkModel.getUserNum()) {
                throw new ServiceException("当前登录用户数量大于授权用户数量！");
            }
            log.info("证书校验通过，证书有效期：{} - {}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter()));
            return true;
        }catch (Exception e){
            log.error("证书校验失败！",e);
            throw new ServiceException("证书校验失败，请联系管理员！"+e.getMessage());
        }
    }

    /**
     * 初始化证书生成参数
     * @param param License校验类需要的参数
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param){
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }
}
