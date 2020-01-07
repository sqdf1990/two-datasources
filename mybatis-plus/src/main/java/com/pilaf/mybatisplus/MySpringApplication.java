package com.pilaf.mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 演示简单的双数据源，即不需要跨库事务的场景，支持单库事务。
 * @author: pilaf
 * @create: 2020-01-06 17:32
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.pilaf.mybatisplus.db1.mapper","com.pilaf.mybatisplus.db2.mapper"})
public class MySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class, args);
    }

}
