package com.crow.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FootDataMapper {

    @Insert("insert into foot_data (`name`,`winData`,`flatData`,`lossData`,`result`,`type`,`createTime`,`footName`) values " +
            "(#{name},#{winData},#{flatData},#{lossData},#{result},#{type},#{createTime},#{footName})")
    void insert(FootData data);

    @Select("SELECT * "
            + " FROM foot_data "
            + " WHERE type = #{type} "
            + " AND footName = #{footName}"
            + " AND result is not null")
    List<FootData> list(@Param("type") String type, @Param("footName") String footName);
}
