package com.example.backendframework.Dao.wardrobeDao;

import com.example.backendframework.Model.Clothing;
import com.example.backendframework.Model.MatchClothing;
import com.example.backendframework.Model.Subtype;
import com.example.backendframework.Model.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WardrobeDao {
    @Select("SELECT * FROM subtype WHERE type_id=#{typeId}")
    List<Subtype> getSubtype(int typeId);

    @Select("SELECT * FROM clothing WHERE user_id=#{userId} AND subtype_id=#{subtypeId}")
    List<Clothing> getClothing(int userId,int subtypeId);

    @Insert("INSERT INTO clothing VALUES(#{id},#{clothingPic},#{userId},#{subtypeId})")
    void addClothing(int id,String clothingPic,int userId,int subtypeId);

    @Select("SELECT * FROM type")
    List<Type> getType();

    @Update("UPDATE clothing SET subtype_id=#{subtypeId} WHERE id=#{clothingId}")
    void modifyClothing(int clothingId,int subtypeId);

    @Delete("DELETE FROM clothing WHERE id=#{clothingId}")
    void deleteClothing(int clothingId);

    @Select("SELECT * FROM match_clothing WHERE clothing_id=#{clothingId}")
    List<MatchClothing> getMatchClothing(int clothingId);

    @Insert("INSERT INTO clothing(clothing_pic,user_id,subtype_id) VALUES(#{clothingPic},#{userId},#{subtypeId})")
    void importPic(String clothingPic,int userId,int subtypeId);

}
