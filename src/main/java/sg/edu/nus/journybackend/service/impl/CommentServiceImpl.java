package sg.edu.nus.journybackend.service.impl;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Customer;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.mapper.CommentMapper;
import sg.edu.nus.journybackend.repository.CommentRepository;
import sg.edu.nus.journybackend.repository.CustomerRepository;
import sg.edu.nus.journybackend.repository.PostRepository;
import sg.edu.nus.journybackend.dto.CommentDto;
import sg.edu.nus.journybackend.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CustomerRepository customerRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto, String postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        Customer commenter = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with username: " + username));

        Comment comment = CommentMapper.mapToComment(commentDto);

        commenter.getComments().add(comment);
        post.getCommentList().add(comment);

        comment.setPost(post);
        comment.setCommenter(commenter);

        comment = commentRepository.save(comment);
        customerRepository.save(commenter);
        postRepository.save(post);

        return CommentMapper.mapToCommentDto(comment);
    }

    @Override
    public List<CommentDto> retrieveCommentsByPostId(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        List<Comment> allComments = post.getCommentList();

        return allComments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<CommentDto> retrieveCommentsByUsername(String username) {
        Customer commenter = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        List<Comment> allComments = commenter.getComments();

        return allComments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(String commentId) {
        Comment toBeDeleted = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        Customer commenter = toBeDeleted.getCommenter();
        commenter.getComments().remove(toBeDeleted);
        customerRepository.save(commenter);

        Post post = toBeDeleted.getPost();
        post.getCommentList().remove(toBeDeleted);
        postRepository.save(post);

        commentRepository.deleteById(commentId);
    }

    //We do not allow customers to update comment.
}