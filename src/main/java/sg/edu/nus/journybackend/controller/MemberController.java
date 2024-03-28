package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.exception.InvalidCredentialException;
import sg.edu.nus.journybackend.service.MemberService;
import sg.edu.nus.journybackend.service.PostService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private MemberService memberService;
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody Member newMember) {
        try{
            Member savedMember = memberService.registerCustomer(newMember);

            return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // This API accepts request as form-data, NOT json object!
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try{
            Member loginMember = memberService.login(username, password);

            return new ResponseEntity<>(loginMember, HttpStatus.OK);
        } catch (InvalidCredentialException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{memberId}/posts")
    public ResponseEntity<?> createPost(@PathVariable("memberId") Long memberId, @RequestBody Post post) {
        try {
            Post createdPost = postService.createPost(memberId, post);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{memberId}/posts/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("memberId") Long memberId, @PathVariable("postId") Long postId, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(memberId, postId, post);
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
            return ResponseEntity.ok(postService.retrievePostsByMemberId(memberId));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
