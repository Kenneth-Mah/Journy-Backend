package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.service.CommentService;
import sg.edu.nus.journybackend.service.MemberService;
import sg.edu.nus.journybackend.service.PostService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private MemberService memberService;
    private PostService postService;
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestBody Post post
    ) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.findByUsername(username).getMemberId();

            Post createdPost = postService.createPost(memberId, post);

            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok(String.format("PostID: %s deleted successfully", postId));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> retrievePostById(@PathVariable("postId") Long postId) {
        try {
            Post post = postService.retrievePostById(postId);

            for (Comment comment : post.getComments()) {
                detachCommenter(comment);
            }

            return ResponseEntity.ok(post);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllPosts() {
        List<Post> posts = postService.retrieveAllPosts();

        for (Post post : posts) {
            for (Comment comment : post.getComments()) {
                detachCommenter(comment);
            }
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> retrieveCommentsByPostId(@PathVariable("postId") Long postId) {
        try {
            List<Comment> comments = commentService.retrieveCommentsByPostId(postId);

            for (Comment comment : comments) {
                //prevent circular referencing
                comment.getCommenter().setComments(new ArrayList<>());
                comment.getCommenter().setPosts(new ArrayList<>());
            }

            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void detachCommenter(Comment comment) {
        Member commenter = comment.getCommenter();

        commenter.setPassword("");
        commenter.setComments(new ArrayList<>());
        commenter.setPosts(new ArrayList<>());
    }
}
