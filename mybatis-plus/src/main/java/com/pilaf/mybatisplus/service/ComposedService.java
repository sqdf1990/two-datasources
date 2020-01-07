package com.pilaf.mybatisplus.service;

import com.pilaf.mybatisplus.db1.entity.Student;
import com.pilaf.mybatisplus.db2.entity.Teacher;

/**
 * @description: 涉及到更改db1和db2的服务
 * @author: pilaf
 * @create: 2020-01-06 20:17
 */
public interface ComposedService {

    /**
     * 在一个方法中同时修改db1，db2中的表
     * @param student
     * @param teacher
     */
    void insert(Student student, Teacher teacher);

    void insertStudent(Student student, Teacher teacher);
}
