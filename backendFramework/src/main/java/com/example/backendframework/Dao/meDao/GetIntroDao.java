package com.example.backendframework.Dao.meDao;

import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GetIntroDao {
    @Select("SELECT * from user WHERE user.id = #{user_id}")
    User getIntro(int user_id);

}
