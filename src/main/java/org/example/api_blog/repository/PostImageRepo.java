package org.example.api_blog.repository;

import org.apache.ibatis.annotations.*;
import org.example.api_blog.model.entity.PostImage;

import java.util.List;

@Mapper
public interface PostImageRepo {
    @Insert("""
        <script>
            INSERT INTO post_image(post_id, image_url)
            VALUES
            <foreach collection="req" item="image" separator=",">
                (#{image.postId}, #{image.imageUrl})
            </foreach>
        </script>
    """)
    @Options(useGeneratedKeys = true, keyProperty = "imageId", keyColumn = "image_id")
    void insertImage(@Param("req") List<PostImage> images);
}
