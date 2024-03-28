package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.service.PostService;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

//    @DeleteMapping("/{postId}")
//    public ResponseEntity<?> deletePost(@PathVariable("postId") String postId) {
//        try {
//            postService.deletePost(postId);
//            return ResponseEntity.ok(String.format("PostID: %s deleted successfully", postId));
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping
    public ResponseEntity<?> retrieveAllPosts() {
        List<Post> posts = postService.retrieveAllPosts();
        return ResponseEntity.ok(posts);
    }
}
