package com.example.backendframework.Dao.login_registerDao;

import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    @Select("select * from user where user_account=#{user_account}")
    List<User> accountFindUser(String user_account);

    @Update("update user set user_nickname=#{user_nickname}," +
            "user_sex=#{user_sex}," +
            "user_pic_path=#{user_pic_path}," +
            "user_profile=#{user_profile} " +
            "where id=#{id}")
    int updateUserIfo(User user1);

    @Update("update user set user_token=#{user_token} where user_account=#{user_account}")
    void updateToken(String user_account,String user_token);

    @Select("select * from user where user_token=#{user_token}")
    User findToken(String user_token);
}
