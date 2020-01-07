package com.pilaf.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 单元测试启动类
 * @author: pilaf
 * @create: 2020-01-06 20:12
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.pilaf.mybatisplus.db1.mapper","com.pilaf.mybatisplus.db2.mapper"})
public class MyTestSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTestSpringApplication.class, args);
    }
}
