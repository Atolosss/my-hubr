package com.example.repository;

import com.example.model.entity.Post;
import com.example.model.enums.PostSortType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll(PostSortType sort);
}
