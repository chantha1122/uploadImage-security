package org.example.api_blog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private long postId;
    private String title;
    private String description;
    private long userId;
    private LocalDateTime createdAt;
}
