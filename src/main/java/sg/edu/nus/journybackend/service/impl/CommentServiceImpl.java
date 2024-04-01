package sg.edu.nus.journybackend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.Comment;
import sg.edu.nus.journybackend.entity.Member;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.repository.CommentRepository;
import sg.edu.nus.journybackend.repository.MemberRepository;
import sg.edu.nus.journybackend.repository.PostRepository;
import sg.edu.nus.journybackend.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    @PersistenceContext
    private EntityManager em;

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private MemberRepository memberRepository;

    @Override
    public Comment createComment(Comment comment, String username, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        Member commenter = memberRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with username: " + username));

        commenter.getComments().add(comment);
        post.getComments().add(comment);

        comment.setPost(post);
        comment.setCommenter(commenter);

        comment = commentRepository.save(comment);
        memberRepository.save(commenter);
        postRepository.save(post);

        em.detach(comment);
        comment.getCommenter().setComments(new ArrayList<>());
        comment.getCommenter().setPosts(new ArrayList<>());

        return comment;
    }

    @Override
    public List<Comment> retrieveCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        List<Comment> allComments = post.getComments();

        for (Comment comment : allComments) {
            em.detach(comment);

            //prevent circular referencing
            comment.getCommenter().setComments(new ArrayList<>());
            comment.getCommenter().setPosts(new ArrayList<>());
        }

        return allComments;
    }

    @Override
    public List<Comment> retrieveCommentsByUsername(String username) {
        Member commenter = memberRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        List<Comment> allComments = commenter.getComments();

        for (Comment comment : allComments) {
            em.detach(comment);
            comment.getCommenter().setComments(new ArrayList<>());
            comment.getCommenter().setPosts(new ArrayList<>());
        }

        return allComments;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment toBeDeleted = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        Member commenter = toBeDeleted.getCommenter();
        commenter.getComments().remove(toBeDeleted);
        memberRepository.save(commenter);

        Post post = toBeDeleted.getPost();
        post.getComments().remove(toBeDeleted);
        postRepository.save(post);

        commentRepository.deleteById(commentId);
    }

    //We do not allow customers to update comment.
}
