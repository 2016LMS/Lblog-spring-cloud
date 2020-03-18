package com.lms.Lblog.spring.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Autor Lms
 * Time 2020-3-14
 */
@SpringBootApplication
//开启eureka服务端，也就是eureka server负责服务的注册和发现
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
