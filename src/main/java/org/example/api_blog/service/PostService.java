package org.example.api_blog.service;

import org.example.api_blog.model.dto.request.PostRequest;
import org.example.api_blog.model.dto.response.PostResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostResponse addPost(PostRequest postRequest, MultipartFile[] files);
}
