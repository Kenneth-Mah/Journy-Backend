package sg.edu.nus.journybackend.mapper;

import sg.edu.nus.journybackend.dto.CommentDto;
import sg.edu.nus.journybackend.entity.Comment;

public class CommentMapper {
    public static CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getCommentDateTime(),
                comment.getCommentDetails(),
                comment.getCommenter(),
                comment.getPost()
        );
    }

    public static Comment mapToComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getCommentId(),
                commentDto.getCommentDateTime(),
                commentDto.getCommentDetails(),
                commentDto.getCommenter(),
                commentDto.getPost()
        );
    }
}
