package com.pilaf.mybatis.service.db1;

import com.pilaf.mybatis.db1.entity.Student;
import com.pilaf.mybatis.db1.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: dufeng3
 * @create: 2020-01-06 19:33
 */
@Service
public class Db1ServiceImpl implements Db1Service {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void insert(Student student) {
        studentMapper.insert(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<Student> studentList) {
        if(studentList==null||studentList.size()<2){
            System.out.println("为了模拟事务，请保证list元素个数大于等于2");
        }
        for (int i = 0; i < studentList.size(); i++) {
            studentMapper.insert(studentList.get(i));
            if(i==1){
                //模拟一个会导致事务回滚的异常
                throw new RuntimeException("模拟一个异常");
            }
        }
    }
}
