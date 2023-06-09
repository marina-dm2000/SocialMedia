package com.example.socialMedia.service;

import com.example.socialMedia.dto.comment.CommentRequestDTO;
import com.example.socialMedia.dto.comment.CommentUpdateRequestDTO;
import com.example.socialMedia.model.Comment;
import com.example.socialMedia.model.Post;
import com.example.socialMedia.model.User;
import com.example.socialMedia.repository.CommentRepository;
import com.example.socialMedia.repository.PostRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Long postId, CommentRequestDTO commentRequestDTO, User user) {
        Post post = postRepository.getById(postId);
        Comment comment = new Comment();
        comment.setText(commentRequestDTO.getText());
        comment.setSender(user);
        comment.setPost(post);
        comment.setSendDate(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return savedComment;
    }

    public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Post not found with ID: " + postId));
        return commentRepository.findByPost(post);
    }

    public Comment updateComment(Long commentId, CommentUpdateRequestDTO commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Comment not found with ID: " + commentId));

        comment.setText(commentUpdateRequest.getText());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Comment not found with ID: " + commentId));
        commentRepository.delete(comment);
    }

}