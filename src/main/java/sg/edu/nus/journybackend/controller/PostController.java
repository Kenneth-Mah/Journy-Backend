package sg.edu.nus.journybackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.KMLFile;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.InvalidCredentialException;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.service.CommentService;
import sg.edu.nus.journybackend.service.KMLFileService;
import sg.edu.nus.journybackend.service.MemberService;
import sg.edu.nus.journybackend.service.PostService;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;
    private final KMLFileService kmlFileService;

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestBody Post post
    ) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            Post createdPost = postService.createPost(memberId, post);
            processPostForResponse(createdPost);

            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody Post post
    ) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            Post updatedPost = postService.updatePost(memberId, postId, post);

            processPostForResponse(updatedPost);

            return ResponseEntity.ok(updatedPost);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidCredentialException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveMyPosts() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            List<Post> allPosts = postService.retrievePostsByMemberId(memberId);

            for (Post post : allPosts) {
                processPostForResponse(post);
            }

            return ResponseEntity.ok(allPosts);
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

            processPostForResponse(post);

            return ResponseEntity.ok(post);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable("postId") Long postId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            postService.likePost(memberId, postId);

            return ResponseEntity.ok(String.format("PostID: %s liked successfully", postId));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable("postId") Long postId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            postService.unlikePost(memberId, postId);

            return ResponseEntity.ok(String.format("PostID: %s unliked successfully", postId));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> retrieveAllPosts() {
        List<Post> posts = postService.retrieveAllPosts();

        for (Post post : posts) {
            processPostForResponse(post);
        }

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody Comment comment
    ) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            Comment newComment = commentService.createComment(comment, memberId, postId);

            detachCommenter(newComment);

            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> retrieveCommentsByPostId(@PathVariable("postId") Long postId) {
        try {
            List<Comment> comments = commentService.retrieveCommentsByPostId(postId);

            for (Comment comment : comments) {
                detachCommenter(comment);
            }

            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{postId}/kml-file")
    public ResponseEntity<?> uploadKMLFileToFileSystem(@PathVariable("postId") Long postId, @RequestBody MultipartFile file) {
        try {
            KMLFile savedKMLFile = kmlFileService.storeIntoFileSystem(postId, file);

            return new ResponseEntity<>(savedKMLFile, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{postId}/kml-file")
    public ResponseEntity<?> retrieveKMLFile(@PathVariable("postId") Long postId) {
        try {
            Post post = postService.retrievePostById(postId);
            KMLFile kmlFile = kmlFileService.downloadKMLFileUsingFileId(post.getKmlFile().getKmlFileId());
            kmlFile.setPost(null);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .header("Content-Disposition", "attachment; filename=\"" + kmlFile.getFileName() + "\"")
                    .body(kmlFile.getFileData());
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //pass it in JSON as { "location", "value" }. key-value pair
    @PostMapping("{postId}/location")
    public ResponseEntity<?> addLocation(@PathVariable("postId") Long postId, @RequestBody JsonNode jsonLocation) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Long memberId = memberService.retrieveMemberByUsername(username).getMemberId();

            String location = jsonLocation.get("location").asText();

            Post post = postService.retrievePostById(postId);

            post = postService.addLocation(memberId, post.getPostId(), location);

            processPostForResponse(post);

            return ResponseEntity.ok(post);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private void processPostForResponse(Post post) {
        Integer likeCount = postService.getLikeCount(post.getPostId());
        post.setLikeCount(likeCount);
        detachMember(post.getCreator());

        if (post.getComments() != null) {
            for (Comment comment : post.getComments()) {
                detachCommenter(comment);
            }
        }
    }

    private void detachMember(Member member) {
        member.setPassword(null);
        member.setFollowersMembers(null);
        member.setFollowingMembers(null);
        member.setPosts(null);
        member.setLikedPosts(null);
        member.setComments(null);
    }

    private void detachCommenter(Comment comment) {
        Member commenter = comment.getCommenter();

        detachMember(commenter);
    }
}
