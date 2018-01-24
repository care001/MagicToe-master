package com.crow.domain;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface FootOddsMapper {

    @Insert("insert into foot_odds (`name`,`vs`,`win`,`flat`,`loss`,`screen`,`matchTime`,`createTime`) values " +
            "(#{name},#{vs},#{win},#{flat},#{loss},#{screen},#{matchTime},#{createTime})")
    void insert(FootOdds footOdds);

    @Select("SELECT * "
            + " FROM foot_odds "
            + " WHERE screen = #{screen} "
            + " AND createTime < #{createTime}"
            + " ORDER BY createTime desc"
            + " LIMIT #{startNo},#{pageSize}")
    List<FootOdds> list(@Param("screen") String screen, @Param("createTime") Date createTime, @Param("pageSize") Integer pageSize, @Param("startNo") Integer startNo);

    @Select("SELECT screen "
            + " FROM foot_odds "
            + " WHERE matchTime > #{start} "
            + " AND matchTime < #{end}"
            + " group by screen ")
    List<String> needHandle(@Param("start") Date start, @Param("end") Date end);
}
