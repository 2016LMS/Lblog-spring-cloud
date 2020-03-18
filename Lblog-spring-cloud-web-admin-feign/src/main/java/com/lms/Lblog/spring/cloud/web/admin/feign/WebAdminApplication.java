package com.lms.Lblog.spring.cloud.web.admin.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Autor Lms
 * Time 2020-3-15
 */
@SpringBootApplication
//作为feign客户端去请求服务
@EnableFeignClients
//作为eureka客户端去发现服务
@EnableDiscoveryClient
public class WebAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAdminApplication.class,args);
    }
}
