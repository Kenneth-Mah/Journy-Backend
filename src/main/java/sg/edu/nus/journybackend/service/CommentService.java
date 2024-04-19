package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment, Long memberId, Long postId);

    List<Comment> retrieveCommentsByPostId(Long postId);

    List<Comment> retrieveCommentsByUsername(String username);

    List<Comment> retrieveCommentsByMemberId(Long memberId);

    void deleteComment(Long commentId);
}
