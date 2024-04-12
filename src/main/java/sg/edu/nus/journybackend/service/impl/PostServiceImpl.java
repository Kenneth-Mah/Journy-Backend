package sg.edu.nus.journybackend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.InvalidCredentialException;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.repository.MemberRepository;
import sg.edu.nus.journybackend.repository.PostRepository;
import sg.edu.nus.journybackend.repository.CommentRepository;
import sg.edu.nus.journybackend.service.PostService;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @PersistenceContext
    private EntityManager em;

    private MemberRepository memberRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    @Override
    //Whenever someone first creates a post, the post will have 0 comments.
    public Post createPost(Long memberId, Post newPost) {
        Member creator = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
        newPost.setCreator(creator);
        postRepository.save(newPost);

        creator.getPosts().add(newPost);
        memberRepository.save(creator);

        return newPost;
    }

    @Override
    public Post updatePost(Long memberId, Long postId, Post editedPost) {
        Post persistedPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        //Check if the member is the creator of the post
        if (!persistedPost.getCreator().getMemberId().equals(memberId)) {
            throw new InvalidCredentialException("Member not authorized to update post with id: " + postId);
        }

        //Post createdDateTime cannot be changed
        //Post id cannot be changed
        //Post creator cannot be changed
        persistedPost.setLikeCount(editedPost.getLikeCount());
        persistedPost.setKmlFile(editedPost.getKmlFile());
        // currPost.setCommentList(postDto.getCommentList());
        postRepository.save(persistedPost);

        persistedPost.getComments().size();

        return persistedPost;
    }

    @Override
    public Post retrievePostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        post.getComments().size();

        return post;
    }

    @Override
    public void deletePost(Long postId) {
        Post toBeDeleted = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        //Delete all associated comments with the post
        //Doesn't work
        List<Comment> commentList = toBeDeleted.getComments();
        for (Comment comment : commentList) {
            comment.getCommenter().getComments().removeIf(c -> c.getCommentId().equals(comment.getCommentId()));
            // commentService.deleteComment(comment.getCommentId());
        }
        commentRepository.deleteAll(commentList);

        Member creator = toBeDeleted.getCreator();
        if (creator != null && creator.getPosts() != null) {
            creator.getPosts().removeIf(p -> p.getPostId().equals(postId));
            memberRepository.save(creator);
        }

        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> retrievePostsByMemberId(Long memberId) {
        Member creator = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        List<Post> allPosts = creator.getPosts();

        for (Post post : allPosts) {
            post.getComments().size();
        }

        return allPosts;
    }

    @Override
    public List<Post> retrieveAllPosts() {
        List<Post> allPosts = postRepository.findAll();

        for (Post post : allPosts) {
            post.getComments().size();
        }

        return allPosts;
    }

}
