package org.example.api_blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.api_blog.model.dto.request.PostRequest;
import org.example.api_blog.model.dto.response.ApiResponse;
import org.example.api_blog.model.dto.response.PostResponse;
import org.example.api_blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/add-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PostResponse>> addPost(
            @RequestPart("data") String data,
            @RequestPart("files") MultipartFile[] files) throws Exception {

        PostRequest postRequest = objectMapper.readValue(data, PostRequest.class);

        PostResponse post = postService.addPost(postRequest, files);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Add post successfully",
                        post,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }
}
