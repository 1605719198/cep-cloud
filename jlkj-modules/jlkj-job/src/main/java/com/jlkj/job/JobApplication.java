package com.jlkj.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.jlkj.common.security.annotation.EnableCustomConfig;
import com.jlkj.common.security.annotation.EnableRyFeignClients;
import com.jlkj.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 *
 * @author jlkj
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class JobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(JobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
