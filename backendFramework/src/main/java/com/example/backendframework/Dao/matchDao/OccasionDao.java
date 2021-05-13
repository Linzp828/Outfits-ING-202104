package com.example.backendframework.Dao.matchDao;

import com.example.backendframework.Model.Occasion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OccasionDao {
    @Select("select * from occasion where user_id=#{user_id}")
    List<Occasion> listOccasion(int user_id);

    @Insert("insert into occasion(occasion_name,user_id) value(#{occasion_name},#{user_id})")
    int insertOccasion(String occasion_name,int user_id);

    @Delete("delete from occasion where id=#{occasion_id}")
    int deleteOccasion(int occasion_id);

    @Update("update occasion set occasion_name=#{occasion_name} where id=#{occasion_id}")
    int updateOccasion(String occasion_name,int occasion_id);

    @Select("select * from occasion where id=#{occasion_id}")
    Occasion getOccasion(int occasion_id);
}
