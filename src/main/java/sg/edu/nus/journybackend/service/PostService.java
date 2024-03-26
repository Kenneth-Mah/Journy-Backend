package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, String username);
    PostDto updatePost(String postId, PostDto postDto);
    // void deletePost(String postId);
    List<PostDto> retrievePostsByUsername(String username);
}
