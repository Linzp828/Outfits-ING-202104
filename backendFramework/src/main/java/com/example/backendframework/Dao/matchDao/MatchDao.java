package com.example.backendframework.Dao.matchDao;

import com.example.backendframework.Model.Match;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Mapper
public interface MatchDao {
    @Insert("insert into `match`(occasion_id,introduce,user_id) value(#{occasion_id},#{introduce},#{user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMatch(Match match);

    @Delete("delete from `match` where id=#{match_id}")
    int deleteMatch(int match_id);

    @Select("select count(*) from `match` where occasion_id=#{occasion_id} limit 1")
    int existOccasion(int occasion_id);

    @Select("select * from `match` where occasion_id=#{occasion_id}")
    List<Match> listMatch(int occasion_id);
}
