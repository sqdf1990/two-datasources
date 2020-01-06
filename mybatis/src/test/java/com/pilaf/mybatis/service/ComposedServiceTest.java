package com.pilaf.mybatis.service;

import com.pilaf.mybatis.MyTestSpringApplication;
import com.pilaf.mybatis.db1.entity.Student;
import com.pilaf.mybatis.db2.entity.Teacher;
import com.pilaf.mybatis.service.db1.Db1Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: dufeng3
 * @create: 2020-01-06 20:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyTestSpringApplication.class, ComposedServiceTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ComposedServiceTest {

    @Autowired
    private ComposedService composedService;

    @Test
    public void should_insert(){
        Student student = new Student();
        student.setName("张无忌");
        student.setAge(50);

        Teacher teacher = new Teacher();
        teacher.setName("张三丰");
        teacher.setStudentCnt(5);

        composedService.insert(student,teacher);
    }
}
