package com.pilaf.mybatis.service.db2;

import com.pilaf.mybatis.db2.entity.Teacher;
import com.pilaf.mybatis.db2.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: dufeng3
 * @create: 2020-01-06 19:41
 */
@Service
public class Db2ServiceImpl implements Db2Service {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void insert(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<Teacher> teacherList) {
        if(teacherList==null||teacherList.size()<2){
            System.out.println("为了模拟事务，请保证list元素个数大于等于2");
        }
        for (int i = 0; i < teacherList.size(); i++) {
            teacherMapper.insert(teacherList.get(i));
            if(i==1){
                //模拟一个会导致事务回滚的异常
                throw new RuntimeException("模拟一个异常");
            }
        }
    }
}
