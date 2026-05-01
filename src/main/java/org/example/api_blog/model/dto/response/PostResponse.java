package org.example.api_blog.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.api_blog.model.entity.Auth;
import org.example.api_blog.model.entity.PostImage;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private long postId;
    private String title;
    private String description;
    private Auth user;
    private List<PostImage> images;
}
