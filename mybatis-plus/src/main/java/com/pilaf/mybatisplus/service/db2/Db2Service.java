package com.pilaf.mybatisplus.service.db2;


import com.pilaf.mybatisplus.db2.entity.Teacher;

import java.util.List;

/**
 * @description:
 * @author: pilaf
 * @create: 2020-01-06 19:29
 */
@SuppressWarnings("all")
public interface Db2Service {

    void insert(Teacher teacher);

    void batchInsert(List<Teacher> teacherList);
}
