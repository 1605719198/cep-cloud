package com.jlkj.rabbit;

import com.jlkj.common.security.annotation.EnableCustomConfig;
import com.jlkj.common.security.annotation.EnableRyFeignClients;
import com.jlkj.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author xin
 */

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class RabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  消息中心模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
