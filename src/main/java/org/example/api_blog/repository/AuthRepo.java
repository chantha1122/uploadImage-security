package org.example.api_blog.repository;

import org.apache.ibatis.annotations.*;
import org.example.api_blog.model.dto.request.RegisterRequest;
import org.example.api_blog.model.entity.Auth;

@Mapper
public interface AuthRepo {

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results(id = "AuthMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "tokenVersion", column = "token_version"),
            @Result(property = "createdAt",column = "created_at")
    })
    Auth findByEmail(String email);


    @Select("""
        INSERT INTO users (user_name, email, password,created_at)
        VALUES (#{userName}, #{email}, #{password},now()) returning *
    """)
    @ResultMap("AuthMapper")
    Auth register( Auth auth);

    @Select("""
        UPDATE users
            SET token_version = token_version + 1
            WHERE user_id = #{userId} RETURNING token_version;
    """)
    Integer incrementTokenVersion(long userId);


    @Select("""
        SELECT * FROM users WHERE user_id = #{userId}
    """)
    @ResultMap("AuthMapper")
    Auth findByUserId(long userId);
}
