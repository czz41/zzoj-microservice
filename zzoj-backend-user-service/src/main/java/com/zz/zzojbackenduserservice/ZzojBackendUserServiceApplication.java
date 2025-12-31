package com.zz.zzojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.zz.zzojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.zz")
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.zz.zzojbackendserviceclient.service"})
public class ZzojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzojBackendUserServiceApplication.class, args);
    }

}
