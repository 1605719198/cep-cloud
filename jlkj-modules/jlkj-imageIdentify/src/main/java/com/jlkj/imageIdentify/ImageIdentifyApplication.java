package com.jlkj.imageIdentify;


import com.jlkj.common.security.annotation.EnableCustomConfig;
import com.jlkj.common.security.annotation.EnableRyFeignClients;
import com.jlkj.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件服务
 *
 * @author jlkj
 */
@EnableCustomConfig
@EnableCustomSwagger2
@SpringBootApplication
@EnableRyFeignClients
/**
 * 获取token类
 */

public class ImageIdentifyApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ImageIdentifyApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  图像识别模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}

