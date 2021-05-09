package com.example.backendframework.Dao.meDao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ModifyInfoDao {
    @Update("UPDATE user SET user_profile = #{user_profile},user_sex = #{user_sex},user_nickname = #{user_nickname} WHERE user.id = #{user_id}")
    void modifyInfo(int user_id,String user_nickname,String user_sex,String user_profile);

    @Update("UPDATE user set user_pic_path = #{user_pic_path} where user.id = #{user_id}")
    void setHeadPic(int user_id,String user_pic_path);
}
