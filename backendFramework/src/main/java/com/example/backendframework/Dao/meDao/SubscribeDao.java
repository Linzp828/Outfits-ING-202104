package com.example.backendframework.Dao.meDao;


import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubscribeDao {
    @Select("select * from user INNER JOIN subscribe_user on user.id=subscribe_user.subscribe_user_id where subscribe_user.user_id=#{user_id};")
    List<User> getSubscription(int user_id);

    @Select("select * from user INNER JOIN subscribe_user on user.id=subscribe_user.user_id where subscribe_user.subscribe_user_id=#{user_id};")
    List<User> getSubscriber(int user_id);
}
