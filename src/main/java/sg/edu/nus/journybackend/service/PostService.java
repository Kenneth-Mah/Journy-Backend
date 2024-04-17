package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(Long memberId, Post newPost);

    Post updatePost(Long memberId, Long postId, Post post);

    Post addLocation(Long memberId, Long postId, String location);

    Post addLocations(Long memberId, Long postId, List<String> location);

    Post retrievePostById(Long postId);

    void deletePost(Long postId);

    List<Post> retrievePostsByMemberId(Long memberId);

    List<Post> retrieveAllPosts();

    void likePost(Long memberId, Long postId);
}
