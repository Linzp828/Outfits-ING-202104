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
    /**
     * @author: LinZipeng
     * @description: 添加match_clothing表项
     * @params: clothing_id match_id
     * @return: null
     */
    @Insert("insert into match_clothing(clothing_id,match_id) value(#{clothing_id},#{match_id})")
    void insertMatchClothing(int clothing_id, int match_id);

    /**
     * @author: LinZipeng
     * @description: 删除match_clothing中包含某match_id的表项
     * @params: match_id
     * @return: int
     */
    @Delete("delete from match_clothing where match_id=#{match_id}")
    int deleteMatchClothing(int match_id);

    /**
     * @author: LinZipeng
     * @description: 连表查询出某match_id关联的所有Clothing对象
     * @params: match_id
     * @return: List<CLothing>
     */
    @Select("select * from clothing inner join match_clothing on match_clothing.clothing_id=clothing.id where match_id=#{match_id} ")
    List<Clothing> listClothing(int match_id);
}
