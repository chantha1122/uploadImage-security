package org.example.api_blog.repository;

import org.apache.ibatis.annotations.*;
import org.example.api_blog.model.entity.Post;

@Mapper
public interface PostRepo {

    @Insert("""
        INSERT INTO posts(title, description, user_id,created_at)
        VALUES (#{title}, #{description}, #{userId},NOW())
    """)
    @Options(useGeneratedKeys = true, keyProperty = "postId", keyColumn = "post_id")
    void addPost(Post post);
}
