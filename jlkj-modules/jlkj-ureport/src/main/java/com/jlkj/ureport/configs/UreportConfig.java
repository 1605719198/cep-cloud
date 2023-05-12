package com.jlkj.ureport.configs;

import com.bstek.ureport.console.UReportServlet;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zyf
 * @Description
 * @create 2022-07-12 8:31
 */
@ImportResource("classpath:ureport-console-context.xml")
@Configuration
@EnableAutoConfiguration
@Slf4j
public class UreportConfig implements BuildinDatasource {
    private static final String NAME = "cep-cloud数据源";

    @Resource
    public DataSource dataSource;

    /**
     * 定义ureport的启动servlet
     * @return
     */
    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean ureportServlet() {
        return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        }
        return null;
    }

}
