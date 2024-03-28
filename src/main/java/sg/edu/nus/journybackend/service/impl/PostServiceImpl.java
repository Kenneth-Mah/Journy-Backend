package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.dto.PostDto;
import sg.edu.nus.journybackend.entity.Customer;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.mapper.PostMapper;
import sg.edu.nus.journybackend.repository.CustomerRepository;
import sg.edu.nus.journybackend.repository.PostRepository;
import sg.edu.nus.journybackend.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private CustomerRepository customerRepository;
    private PostRepository postRepository;
    // private CommentRepository commentRepository;

    @Override
    //Whenever someone first creates a post, the post will have 0 comments.
    public PostDto createPost(PostDto postDto, String username) {
        Customer creator = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        postDto.setCommentList(new ArrayList<>());

        Post post = PostMapper.mapToPost(postDto);
        post.setCreator(creator);

        post = postRepository.save(post);

        creator.getPosts().add(post);

        customerRepository.save(creator);

        return PostMapper.mapToPostDto(post);
    }

    @Override
    public PostDto updatePost(String postId, PostDto postDto) {
        Post currPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        //Post createdDateTime cannot be changed
        //Post id cannot be changed
        //Post creator cannot be changed
        currPost.setLikeCount(postDto.getLikeCount());
        currPost.setKmlFile(postDto.getKmlFile());
        // currPost.setCommentList(postDto.getCommentList());
        postRepository.save(currPost);

        return PostMapper.mapToPostDto(currPost);
    }

//    @Override
//    public void deletePost(String postId) {
//        Post toBeDeleted = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
//
//        //Delete all associated comments with the post
//        //Doesn't work
//        List<Comment> commentList = toBeDeleted.getCommentList();
//        for (Comment comment : commentList) {
//            comment.getCommenter().getComments().removeIf(c -> c.getCommentId().equals(comment.getCommentId()));
//            // commentService.deleteComment(comment.getCommentId());
//        }
//        commentRepository.deleteAll(commentList);
//
//        Customer creator = toBeDeleted.getCreator();
//        if (creator != null && creator.getPosts() != null) {
//            creator.getPosts().removeIf(p -> p.getPostId().equals(postId));
//            customerRepository.save(creator);
//        }
//
//        postRepository.deleteById(postId);
//    }

    @Override
    public List<PostDto> retrievePostsByUsername(String username) {
        Customer creator = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        List<Post> allPosts = creator.getPosts();

        for (Post posts : allPosts) {
            posts.setCommentList(postRepository.findById(posts.getPostId()).orElseThrow(
                    () -> new ResourceNotFoundException("Post not found with postId: " + posts.getPostId())
            ).getCommentList());
        }

        return allPosts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

}
