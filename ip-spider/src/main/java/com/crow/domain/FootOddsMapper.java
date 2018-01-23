package com.crow.domain;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FootOddsMapper {

    @Insert("insert into foot_odds (`name`,`vs`,`win`,`flat`,`loss`,`screen`,`time`,`create`) values " +
            "(#{name},#{vs},#{win},#{flat},#{loss},#{screen},#{time},#{create})")
    void insert(FootOdds footOdds);
}
