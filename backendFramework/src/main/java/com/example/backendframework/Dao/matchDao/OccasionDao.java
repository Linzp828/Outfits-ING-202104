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

    @Delete("delete from occasion where user_id=#{user_id} and occasion_name=#{occasion_name}")
    int deleteOccasion(int user_id,String occasion_name);

    @Update("update occasion set occasion_name=#{occasion_name} where id=#{occasion_id}")
    int updateOccasion(String occasion_name,int occasion_id);
}
