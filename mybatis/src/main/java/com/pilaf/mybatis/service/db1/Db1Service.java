package com.pilaf.mybatis.service.db1;

import com.pilaf.mybatis.db1.entity.Student;

import java.util.List;

/**
 * @description:
 * @author: pilaf
 * @create: 2020-01-06 19:28
 */
@SuppressWarnings("all")
public interface Db1Service {

    void insert(Student student);

    void batchInsert(List<Student> studentList);
}
