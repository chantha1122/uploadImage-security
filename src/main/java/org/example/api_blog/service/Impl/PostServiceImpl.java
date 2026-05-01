package org.example.api_blog.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.api_blog.model.dto.request.PostRequest;
import org.example.api_blog.model.dto.response.PostResponse;
import org.example.api_blog.model.entity.Auth;
import org.example.api_blog.model.entity.Post;
import org.example.api_blog.model.entity.PostImage;
import org.example.api_blog.repository.AuthRepo;
import org.example.api_blog.repository.PostImageRepo;
import org.example.api_blog.repository.PostRepo;
import org.example.api_blog.service.PinataService;
import org.example.api_blog.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final PinataService pinataService;
    private final PostImageRepo postImageRepo;
    private final AuthRepo authRepo;

    @Override
    @Transactional
    public PostResponse addPost(PostRequest request, MultipartFile[] files) {
        Post post = new Post();
        BeanUtils.copyProperties(request, post);

        // 1. Insert post
        postRepo.addPost(post);
        long postId = post.getPostId();

        // 2. Fetch user information
        Auth auth = authRepo.findByUserId(request.getUserId());

        // 3. Process image uploads
        List<PostImage> imageEntities = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>(); // To store just the strings

        for(MultipartFile file : files) {
            String url = pinataService.uploadFile(file);

            // Add to URL list for the Response
            imageUrls.add(url);

            // Create Entity for the Database
            PostImage postImage = new PostImage();
            postImage.setPostId(postId);
            postImage.setImageUrl(url);
            imageEntities.add(postImage);
        }

        // 4. Batch insert entities into Database
        if(!imageEntities.isEmpty()){
            postImageRepo.insertImage(imageEntities);
        }

        // 5. Return the response with the list of strings
        return PostResponse.builder()
                .postId(postId)
                .title(request.getTitle())
                .description(request.getDescription())
                .user(auth)
                .images(imageEntities) // Pass the List<String> here
                .build();
    }
}
