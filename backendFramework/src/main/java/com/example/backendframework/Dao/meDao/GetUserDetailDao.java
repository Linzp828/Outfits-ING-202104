package com.example.backendframework.Dao.meDao;


import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GetUserDetailDao {
    @Select("SELECT * from user WHERE user.id = #{user_id}")
    User getDetail(int user_id);

}
