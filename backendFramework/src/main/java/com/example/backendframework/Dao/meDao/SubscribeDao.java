package com.example.backendframework.Dao.meDao;


import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubscribeDao {
    @Select("select * from user INNER JOIN subscribe_user on user.id=subscribe_user.subscribe_user_id where subscribe_user.user_id=#{user_id};")
    List<User> getSubscription(int user_id);

    @Select("select * from user INNER JOIN subscribe_user on user.id=subscribe_user.user_id where subscribe_user.subscribe_user_id=#{user_id};")
    List<User> getSubscriber(int user_id);

    @Insert("insert into subscribe_user(user_id,subscribe_user_id) value(#{user_id}, #{subscribe_user_id}) ")
    void addSubscribeUser(int user_id,int subscribe_user_id);

    @Delete("delete from subscribe_user where user_id=#{user_id} and subscribe_user_id=#{subscribe_user_id} ")
    void deleteSubscribeUser(int user_id,int subscribe_user_id);


}
