package com.pilaf.mybatis.service;

import com.pilaf.mybatis.MyTestSpringApplication;
import com.pilaf.mybatis.db1.entity.Student;
import com.pilaf.mybatis.service.db1.Db1Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @description:  不是真正的单元测试，真正的单元测试是可重复，与具体某个人的数据库无关，应该随时随地都可以运行。
 * TODO 暂时先不模拟两个H2数据库的情况，后续改成两个H2内存数据库，实现真正的单元测试
 * @author: pilaf
 * @create: 2020-01-06 20:14
 */
@SuppressWarnings("all")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyTestSpringApplication.class, Db1ServiceTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Db1ServiceTest {

    @Autowired
    private Db1Service db1Service;

    @Test
    public void should_insert(){
        Student student = new Student();
        student.setName("鸠摩智");
        student.setAge(50);

        db1Service.insert(student);
    }


    @Test
    public void should_not_insert(){
        Student student1 = new Student();
        student1.setName("天山童姥");
        student1.setAge(60);

        Student student2 = new Student();
        student2.setName("无崖子");
        student2.setAge(70);
        db1Service.batchInsert(Arrays.asList(student1,student2));
    }
}
