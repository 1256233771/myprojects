package cins.art.numproduct.mapper;

import cins.art.numproduct.entity.Theme;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//SELECT * FROM product  WHERE
//uploadTime BETWEEN
//STR_TO_DATE('2016-03-14','%Y-%m-%d')
//AND
//STR_TO_DATE('2019-03-14','%Y-%m-%d')

public interface ThemeMapper {
    @Insert("insert into theme(id,picUrl,title,description,uploadTime) " +
            "values(#{id},#{picUrl},#{title},#{description},#{uploadTime})")
    int add(Theme theme);
    @Select("select * from theme where uploadTime>#{param1} and uploadTime<#{param2}")
    List<Theme> findByTime(String startTime,String endTime);
    @Select("select * from theme where uploadTime<#{param1} limit #{param2}")
    List<Theme> findByTimeLimit(String endTime,Integer limit);//查询最新的limit条数的数据
}
