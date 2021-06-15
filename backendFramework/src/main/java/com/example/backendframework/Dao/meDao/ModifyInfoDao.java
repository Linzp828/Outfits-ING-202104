package com.example.backendframework.Dao.meDao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ModifyInfoDao {

    /**
     * @author:  林龙星
     * @description: 根据传入的信息修改用户信息
     * @param:  user_id,user_nickname,user_sex,user_profile
     * @return: 无
     */
    @Update("UPDATE user SET user_profile = #{user_profile},user_sex = #{user_sex},user_nickname = #{user_nickname} WHERE user.id = #{user_id}")
    void modifyInfo(int user_id,String user_nickname,String user_sex,String user_profile);

    /**
     * @author:  林龙星
     * @description: 根据传入的图片设置用户头像
     * @param:  user_id,user_pic_path
     * @return: 无
     */
    @Update("UPDATE user set user_pic_path = #{user_pic_path} where user.id = #{user_id}")
    void setHeadPic(int user_id,String user_pic_path);
}
