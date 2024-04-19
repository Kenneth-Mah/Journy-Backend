package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.InvalidCredentialException;
import sg.edu.nus.journybackend.exception.InvalidLikeException;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.repository.MemberRepository;
import sg.edu.nus.journybackend.repository.PostRepository;
import sg.edu.nus.journybackend.repository.CommentRepository;
import sg.edu.nus.journybackend.service.PostService;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    //Whenever someone first creates a post, the post will have 0 comments.
    public Post createPost(Long memberId, Post newPost) {
        Member creator = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
        newPost.setCreator(creator);
        newPost.setCreatedDateTime(new Date());
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

        //Post id cannot be changed
        //Post createdDateTime cannot be changed
        //Post creator cannot be changed
        persistedPost.setKmlFile(editedPost.getKmlFile());
        persistedPost.setPostPictureURL(editedPost.getPostPictureURL());
        persistedPost.setTitle(editedPost.getTitle());
        persistedPost.setDescription(editedPost.getDescription());
        persistedPost.setBudget(editedPost.getBudget());
        persistedPost.setLocations(editedPost.getLocations());
        postRepository.save(persistedPost);

        return persistedPost;
    }

    @Override
    public Post addLocation(Long memberId, Long postId, String location) {
        Post persistedPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        //Check if the member is the creator of the post
        if (!persistedPost.getCreator().getMemberId().equals(memberId)) {
            throw new InvalidCredentialException("Member not authorized to update post with id: " + postId);
        }

        List<String> currLocations = persistedPost.getLocations();
        currLocations.add(location);

        persistedPost.setLocations(currLocations);
        postRepository.save(persistedPost);

        return persistedPost;
    }

    @Override
    public Post addLocations(Long memberId, Long postId, List<String> locations) {
        Post persistedPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        //Check if the member is the creator of the post
        if (!persistedPost.getCreator().getMemberId().equals(memberId)) {
            throw new InvalidCredentialException("Member not authorized to update post with id: " + postId);
        }

        List<String> currLocations = persistedPost.getLocations();

        currLocations.addAll(locations);

        persistedPost.setLocations(currLocations);
        postRepository.save(persistedPost);

        return persistedPost;
    }

    @Override
    public Post retrievePostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    @Override
    public void deletePost(Long postId) {
        Post toBeDeleted = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        //Delete all associated comments with the post
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

        return creator.getPosts();
    }

    @Override
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void likePost(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        for (Post memPost : member.getLikedPosts()) {
            if (post.getPostId().equals(memPost.getPostId())) {
                throw new InvalidLikeException(String.format("You have already liked PostId: %s, unable to like", postId));
            }
        }

        member.getLikedPosts().add(post);
        memberRepository.save(member);
    }

    @Override
    public void unlikePost(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        boolean liked = false;

        for (Post memPost : member.getLikedPosts()) {
            if (post.getPostId().equals(memPost.getPostId())) {
                liked = true;
                break;
            }
        }

        if (!liked) {
            throw new InvalidLikeException(String.format("You haven't liked PostId: %s, unable to unlike", postId));
        }

        member.getLikedPosts().remove(post);
        memberRepository.save(member);
    }

    @Override
    public Integer getLikeCount(Long postId) {
        return memberRepository.countByLikedPostsPostId(postId);
    }

}
