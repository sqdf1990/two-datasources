package com.pilaf.mybatis.service;

import com.pilaf.mybatis.db1.entity.Student;
import com.pilaf.mybatis.db1.mapper.StudentMapper;
import com.pilaf.mybatis.db2.entity.Teacher;
import com.pilaf.mybatis.db2.mapper.TeacherMapper;
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
}
