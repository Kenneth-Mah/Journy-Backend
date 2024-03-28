//package sg.edu.nus.journybackend.controller;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sg.edu.nus.journybackend.dto.CommentDto;
//import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
//import sg.edu.nus.journybackend.service.CommentService;
//
//import java.util.List;
//
//@CrossOrigin("*")
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/comments")
//public class CommentController {
//    private CommentService commentService;
//
//    @PostMapping("/create/{postId}")
//    public ResponseEntity<?> createComments(@RequestBody CommentDto commentDto, @PathVariable("postId") Long postId) {
//        try {
//            CommentDto newComment = commentService.createComment(commentDto, postId, commentDto.getCommenter().getUsername());
//            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/post/{postId}")
//    public ResponseEntity<?> retrieveCommentsByPostId(@PathVariable("postId") Long postId) {
//        try {
//            List<CommentDto> comments = commentService.retrieveCommentsByPostId(postId);
//            return ResponseEntity.ok(comments);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/user/{username}")
//    public ResponseEntity<?> retrieveCommentsByUsername(@PathVariable("username") String username) {
//        try {
//            List<CommentDto> comments = commentService.retrieveCommentsByUsername(username);
//            return ResponseEntity.ok(comments);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
//        try {
//            commentService.deleteComment(commentId);
//            return ResponseEntity.ok(String.format("CommentID: %s deleted successfully", commentId));
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
