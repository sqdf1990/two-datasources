package com.pilaf.mybatisplus.service;

import com.pilaf.mybatisplus.MyTestSpringApplication;
import com.pilaf.mybatisplus.db2.entity.Teacher;
import com.pilaf.mybatisplus.service.db2.Db2Service;
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
@SpringBootTest(classes = {MyTestSpringApplication.class, Db2ServiceTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Db2ServiceTest {

    @Autowired
    private Db2Service db2Service;

    @Test
    public void should_insert(){
        Teacher teacher = new Teacher();
        teacher.setName("慕容博");
        teacher.setStudentCnt(1);
        
        db2Service.insert(teacher);
    }
    
    
    @Test
    public void should_not_insert(){
        Teacher teacher1 = new Teacher();
        teacher1.setName("郭靖");
        teacher1.setStudentCnt(60);

        Teacher teacher2 = new Teacher();
        teacher2.setName("黄蓉");
        teacher2.setStudentCnt(3);
        db2Service.batchInsert(Arrays.asList(teacher1,teacher2));
    }
}
