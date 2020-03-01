package com.gqrz.sphereg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan(basePackages = "com.gqrz.sphereg.mapper")
@SpringBootApplication
public class SpheregApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpheregApplication.class, args);
    }

}
