package com.crow.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FootResultMapper {

    @Insert("insert into foot_result (`name`,`info`,`chance`,`result`,`type`,`createTime`,`footName`) values " +
            "(#{name},#{info},#{chance},#{result},#{type},#{createTime},#{footName})")
    void insert(FootResult data);
}
