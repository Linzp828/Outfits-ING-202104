package com.example.backendframework.Dao.matchDao;

import com.example.backendframework.Model.Clothing;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MatchClothingDao {
    @Insert("insert into match_clothing(clothing_id,match_id) value(#{clothing_id},#{match_id})")
    void insertMatchClothing(int clothing_id,int match_id);

    @Delete("delete from match_clothing where match_id=#{match_id}")
    int deleteMatchClothing(int match_id);

    @Select("select * from clothing inner join match_clothing on match_clothing.clothing_id=clothing.id where match_id=#{match_id} ")
    List<Clothing> listClothing(int match_id);
}
