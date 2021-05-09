package com.example.backendframework.Dao.matchDao;

import com.example.backendframework.Model.Match;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Mapper
public interface MatchDao {
    @Insert("insert into `match`(occasion_id,introduce,user_id) value(#{match.occasion_id},#{match.introduce},#{match.user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "match.id")
    int insertMatch(@Param("match") Match match);

    @Delete("delete from `match` where id=#{match_id}")
    int deleteMatch(int match_id);

    @Select("select count(*) from `match` where occasion_id=#{occasion_id} limit 1")
    int existOccasion(int occasion_id);

//    @Select("select * from")
}
