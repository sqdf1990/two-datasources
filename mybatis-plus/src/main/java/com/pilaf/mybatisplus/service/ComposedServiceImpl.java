package com.pilaf.mybatisplus.service;


import com.pilaf.mybatisplus.db1.entity.Student;
import com.pilaf.mybatisplus.db1.mapper.StudentMapper;
import com.pilaf.mybatisplus.db2.entity.Teacher;
import com.pilaf.mybatisplus.db2.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: pilaf
 * @create: 2020-01-06 20:19
 */
@Service
public class ComposedServiceImpl implements ComposedService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void insert(Student student, Teacher teacher) {
        studentMapper.insert(student);
        teacherMapper.insert(teacher);
    }

    @Override
    public void insertStudent(Student student, Teacher teacher) {
        studentMapper.insert(student);
        //模拟一个异常
        int a = 1/0;
        teacherMapper.insert(teacher);
    }
}
