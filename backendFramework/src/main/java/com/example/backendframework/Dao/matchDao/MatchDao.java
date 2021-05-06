package com.example.backendframework.Dao.matchDao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Mapper
public interface MatchDao {
    @Insert("insert into `match`(occasion_id,introduce,user_id) value(#{occasion_id},#{introduce},#{user_id})")
    int insertMatch(int occasion_id, String introduce, int user_id);

    @Delete("delete from `match` where id=#{match_id}")
    int deleteMatch(int match_id);

    @Select("select count(*) from `match` where occasion_id=#{occasion_id} limit 1")
    int existOccasion(int occasion_id);

//    @Select("select * from")
}
