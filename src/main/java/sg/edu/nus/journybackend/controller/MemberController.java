package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.journybackend.auth.AuthenticationRequest;
import sg.edu.nus.journybackend.auth.AuthenticationResponse;
import sg.edu.nus.journybackend.auth.RegisterRequest;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.exception.InvalidCredentialException;
import sg.edu.nus.journybackend.service.CommentService;
import sg.edu.nus.journybackend.service.MemberService;
import sg.edu.nus.journybackend.service.PostService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private MemberService memberService;
    private PostService postService;
    private CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        try{
            AuthenticationResponse response = memberService.register(request);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try{
            AuthenticationResponse response = memberService.authenticate(request);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{memberId}/posts/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("memberId") Long memberId, @PathVariable("postId") Long postId, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(memberId, postId, post);

            for (Comment comment : updatedPost.getComments()) {
                detachCommenter(comment);
            }

            return ResponseEntity.ok(updatedPost);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidCredentialException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{memberId}/posts")
    public ResponseEntity<?> retrievePostsByMemberId(@PathVariable("memberId") Long memberId) {
        try {
            List<Post> allPosts = postService.retrievePostsByMemberId(memberId);

            for (Post post : allPosts) {
                for (Comment comment : post.getComments()) {
                    detachCommenter(comment);
                }
            }

            return ResponseEntity.ok(allPosts);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{memberId}/posts/{postId}/comments")
    public ResponseEntity<?> createComments(@RequestBody Comment comment, @PathVariable("memberId") Long memberId, @PathVariable("postId") Long postId) {
        try {
            Comment newComment = commentService.createComment(comment, memberId, postId);

            newComment.getCommenter().setComments(new ArrayList<>());
            newComment.getCommenter().setPosts(new ArrayList<>());

            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{memberId}/comments")
    public ResponseEntity<?> retrieveCommentsByMemberId(@PathVariable("memberId") Long memberId) {
        try {
            List<Comment> comments = commentService.retrieveCommentsByMemberId(memberId);

            for (Comment comment : comments) {
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

        commenter.setPassword(null);
        commenter.setComments(new ArrayList<>());
        commenter.setPosts(new ArrayList<>());
    }
}
