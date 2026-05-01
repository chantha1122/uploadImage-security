package org.example.api_blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {
    private long imageId;
    private String imageUrl;
    private long postId;
}
