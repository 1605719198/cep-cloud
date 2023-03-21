package com.jlkj.rabbit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.rabbit.entity.TestTable1;
import com.jlkj.rabbit.mapper.TestTable1Mapper;
import com.jlkj.rabbit.service.TestTable1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zyf
* @description 针对表【t_test_table1】的数据库操作Service实现
* @createDate 2022-02-28 15:08:12
*/
@Service
public class TestTable1ServiceImpl extends ServiceImpl<TestTable1Mapper, TestTable1>
    implements TestTable1Service {

    @Autowired
    TestTable1Mapper testTable1Mapper;

    public List<TestTable1> getList() {
        return testTable1Mapper.selectList(null);
    }
}




