package com.example.backendframework.Dao.communityDao;

import com.example.backendframework.Model.Blog;
import com.example.backendframework.Model.CollectedBlog;
import com.example.backendframework.Model.SubscribeUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogDao {
    @Select("select * from blog where id=#{id}")
    List<Blog> blogIdFindBlog(int id);

    @Select("select * from subscribe_user where user_id=#{user_id}")
    List<SubscribeUser> userFindSubscribe(int user_id);

    @Select("select * from blog where user_id=#{user_id}")
    List<Blog> userFindBlog(int user_id);

    @Select("select count(*) from collected_blog where user_id=#{user_id} and blog_id=#{blog_id}")
    int userBlogFind(int  user_id,int blog_id);

    @Delete("delete from collected_blog where user_id=#{user_id} and blog_id=#{blog_id}")
    void userBlogDelete(int  user_id,int blog_id);

    @Insert("insert into collected_blog(user_id,blog_id) value(#{user_id}, #{blog_id})")
    void userBlogInsert(int  user_id,int blog_id);

    @Select("select * from blog where blog_article like concat('%',#{keyword},'%')")
    List<Blog> articleSearch(String keyword);

    @Select("select * from blog where blog_title like concat('%',#{keyword},'%')")
    List<Blog> titleSearch(String keyword);

    @Delete("delete from blog where user_id=#{user_id} and id=#{id}")
    int blogDelete(int  user_id,int id);

    @Select("select * from blog")
    List<Blog> blogSearch();

    @Select("select * from collected_blog where user_id=#{user_id}")
    List<CollectedBlog> userFindBlogId(int user_id);

    @Select("select * from blog where id=#{id}")
    List<Blog> IdFindBlog(int id);


}
