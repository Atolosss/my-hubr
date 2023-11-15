package com.example.repository;

import com.example.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
               select p from Post p
               left join fetch p.comments
        """)
    List<Post> fetchAllPosts();
}
