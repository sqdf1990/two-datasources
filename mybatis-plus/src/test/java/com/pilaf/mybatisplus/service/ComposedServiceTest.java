package com.pilaf.mybatisplus.service;

import com.pilaf.mybatisplus.MyTestSpringApplication;
import com.pilaf.mybatisplus.db1.entity.Student;
import com.pilaf.mybatisplus.db2.entity.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: pilaf
 * @create: 2020-01-06 20:31
 */
@SuppressWarnings("all")
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

    @Test
    public void should_insert_one(){
        Student student = new Student();
        student.setName("裘千仞");
        student.setAge(50);

        Teacher teacher = new Teacher();
        teacher.setName("裘千尺");
        teacher.setStudentCnt(5);

        composedService.insertStudent(student,teacher);
    }
}
