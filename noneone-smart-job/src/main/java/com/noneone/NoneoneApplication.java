package com.noneone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.github.quartzweb.http.QuartzWebServlet;
/**
 * 
 * @author lifeifei
 *
 */
@SpringBootApplication
@ImportResource("classpath:beanRefContext.xml")
//@EnableFeignClients(basePackages = { "com.noneone.feign" })
//@EnableAspectJAutoProxy
//@EnableDiscoveryClient
//@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
//@Configuration
//@EnableScheduling
//@ComponentScan
@ConfigurationProperties(prefix="spring")
public class NoneoneApplication  {
    public static void main(String[] args) {
        SpringApplication.run(NoneoneApplication.class, args);
    }
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new QuartzWebServlet(),"/quartzweb/*");// ServletName默认值为首字母小写，即myServlet
    }
    
}
