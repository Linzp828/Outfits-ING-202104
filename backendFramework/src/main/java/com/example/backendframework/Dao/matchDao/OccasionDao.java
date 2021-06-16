package com.example.backendframework.Dao.matchDao;

import com.example.backendframework.Model.Occasion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OccasionDao {
    /**
     * @author: LinZipeng
     * @description: 列出某用户所有场合
     * @params: user_id
     * @return: List<Occasion>
     */
    @Select("select * from occasion where user_id=#{user_id}")
    List<Occasion> listOccasion(int user_id);

    /**
     * @author: LinZipeng
     * @description: 添加场合
     * @params: occasion_name, user_id
     * @return: int
     */
    @Insert("insert into occasion(occasion_name,user_id) value(#{occasion_name},#{user_id})")
    int insertOccasion(String occasion_name, int user_id);

    /**
     * @author: LinZipeng
     * @description: 删除某场合
     * @params: occasion_id
     * @return: int
     */
    @Delete("delete from occasion where id=#{occasion_id}")
    int deleteOccasion(int occasion_id);

    /**
     * @author: LinZipeng
     * @description: 修改某场合
     * @params: occasion_name, occasion_id
     * @return: int
     */
    @Update("update occasion set occasion_name=#{occasion_name} where id=#{occasion_id}")
    int updateOccasion(String occasion_name, int occasion_id);

    /**
     * @author: LinZipeng
     * @description: 查询某场合的所有信息
     * @params: occasion_id
     * @return: Occasion
     */
    @Select("select * from occasion where id=#{occasion_id}")
    Occasion getOccasion(int occasion_id);
}
