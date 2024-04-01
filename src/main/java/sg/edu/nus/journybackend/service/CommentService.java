package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment, String username, Long postId);
    List<Comment> retrieveCommentsByPostId(Long postId);

    List<Comment> retrieveCommentsByUsername(String username);
    void deleteComment(Long commentId);
}
