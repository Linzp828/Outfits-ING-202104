package com.example.backendframework.Dao.matchDao;

import com.example.backendframework.Model.Match;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Mapper
public interface MatchDao {
    /**
     * @author: LinZipeng
     * @description: 新建搭配
     * @params: Match
     * @return: int
     */
    @Insert("insert into `match`(occasion_id,introduce,user_id) value(#{occasion_id},#{introduce},#{user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMatch(Match match);

    /**
     * @author: LinZipeng
     * @description: 删除某搭配
     * @params: match_id
     * @return: int
     */
    @Delete("delete from `match` where id=#{match_id}")
    int deleteMatch(int match_id);

    /**
     * @author: LinZipeng
     * @description: 判断某场合下是否有搭配
     * @params: occasion_id
     * @return: int
     */
    @Select("select count(*) from `match` where occasion_id=#{occasion_id} limit 1")
    int existOccasion(int occasion_id);

    /**
     * @author: LinZipeng
     * @description: 查询某场合下的所有搭配
     * @params: occasion_id
     * @return: List<Match>
     */
    @Select("select * from `match` where occasion_id=#{occasion_id}")
    List<Match> listMatch(int occasion_id);
}
