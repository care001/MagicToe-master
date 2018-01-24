package com.crow.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FootDataMapper {

    @Insert("insert into foot_data (`name`,`winData`,`flatData`,`lossData`,`result`,`type`,`createTime`) values " +
            "(#{name},#{winData},#{flatData},#{lossData},#{result},#{type},#{createTime})")
    void insert(FootData data);
}
