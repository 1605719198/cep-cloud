package com.jlkj.system.license.verify;

import com.jlkj.system.license.verify.LicenseCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 * @author jlkj
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 自定义过滤器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LicenseCheckInterceptor())
                .addPathPatterns("/notice/list"); //拦截路径
//                .excludePathPatterns("/**");    //放行路径
    }
}
