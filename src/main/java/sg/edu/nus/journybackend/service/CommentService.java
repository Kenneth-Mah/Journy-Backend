package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, String postId, String username);
    List<CommentDto> retrieveCommentsByPostId(String postId);
    List<CommentDto> retrieveCommentsByUsername(String username);
    void deleteComment(String commentId);
}
