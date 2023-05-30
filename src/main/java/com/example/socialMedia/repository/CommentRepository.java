package com.example.socialMedia.repository;

import com.example.socialMedia.model.Comment;
import com.example.socialMedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

}
