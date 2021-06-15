package com.example.backendframework.Dao.meDao;


import com.example.backendframework.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubscribeDao {

    /**
     * @author:  林龙星
     * @description: 根据传入的用户id查询用户的关注列表
     * @param:  user_id
     * @return: List<User>
     */
    @Select("select * from user INNER JOIN subscribe_user on user.id=subscribe_user.subscribe_user_id where subscribe_user.user_id=#{user_id};")
    List<User> getSubscription(int user_id);

    /**
     * @author:  林龙星
     * @description: 根据传入的用户id查询用户的粉丝列表
     * @param:  user_id
     * @return: List<User>
     */
    @Select("select * from user INNER JOIN subscribe_user on user.id=subscribe_user.user_id where subscribe_user.subscribe_user_id=#{user_id};")
    List<User> getSubscriber(int user_id);

    /**
     * @author:  林龙星
     * @description: 根据传入的用户id和要关注的用户的id来添加用户关注关系
     * @param:  user_id，subscribe_user_id
     * @return: 无
     */
    @Insert("insert into subscribe_user(user_id,subscribe_user_id) value(#{user_id}, #{subscribe_user_id}) ")
    void addSubscribeUser(int user_id,int subscribe_user_id);

    /**
     * @author:  林龙星
     * @description: 根据传入的用户id和要关注的用户的id来删除用户关注关系
     * @param:  user_id，subscribe_user_id
     * @return: 无
     */
    @Delete("delete from subscribe_user where user_id=#{user_id} and subscribe_user_id=#{subscribe_user_id} ")
    void deleteSubscribeUser(int user_id,int subscribe_user_id);


}
