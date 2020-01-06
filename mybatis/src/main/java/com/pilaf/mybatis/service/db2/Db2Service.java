package com.pilaf.mybatis.service.db2;

import com.pilaf.mybatis.db2.entity.Teacher;

import java.util.List;

/**
 * @description:
 * @author: dufeng3
 * @create: 2020-01-06 19:29
 */
@SuppressWarnings("all")
public interface Db2Service {

    void insert(Teacher teacher);

    void batchInsert(List<Teacher> teacherList);
}
