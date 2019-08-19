package com.geek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@ImportResource(value = {"classpath:dubbo-consumer.xml"})
public class DubboLearnConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(DubboLearnConsumerApplication.class, args);
        System.out.println("服务消费者启动成功");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
