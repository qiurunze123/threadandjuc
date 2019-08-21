package com.geek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
// 使用 dubbo-provider.xml 配置
@ImportResource(value = {"classpath:dubbo-provider.xml"})
public class DubboLearnServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DubboLearnServerApplication.class, args);
        System.out.println("服务提供者启动成功");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
