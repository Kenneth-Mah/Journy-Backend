package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.service.CommentService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    // This API is to test secured endpoint
    @GetMapping
    public ResponseEntity<?> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok(String.format("CommentID: %s deleted successfully", commentId));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
