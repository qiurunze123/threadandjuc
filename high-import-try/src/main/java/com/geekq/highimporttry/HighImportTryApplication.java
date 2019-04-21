package com.geekq.highimporttry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.geekq.highimporttry.mapper")
@SpringBootApplication
public class HighImportTryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighImportTryApplication.class, args);
    }

}
