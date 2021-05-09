package com.example.backendframework.Dao.login_registerDao;

import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("insert into user(user_account,user_password,user_nickname,user_sex,user_pic_path,user_token) value(#{user_account}, #{user_password}, #{user_nickname}, #{user_sex}, #{user_pic_path},#{user_token})")
    int insertUser(User user);

    @Select("select count(*) from user where user_account=#{#user_account}")
    int phoneFind(String user_account);

    @Select("select * from user order by id desc limit 1")
    User findLastData();

    @Update("update user set user_password=#{user_password} where id=#{id}")
    void updatePassword(String user_password,int id);

    @Select("select * from user where user_account=#{#user_account}")
    User accountFind(String user_account);
}
