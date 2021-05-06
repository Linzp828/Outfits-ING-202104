package com.example.backendframework.Dao.matchDao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MatchClothingDao {
    @Insert("insert into match_clothing(clothing_id,match_id) value(#{clothing_id},#{match_id})")
    int insertMatchClothing(int clothing_id,int match_id);

    @Delete("delete from match_clothing where match_id=#{match_id}")
    int deleteMatchClothing(int match_id);

}
