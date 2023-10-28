package com.example.repository;

import com.example.model.entity.Comment;
import com.example.model.enums.CommentSortType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll(CommentSortType sort);
}
