package com.example.repository;

import com.example.model.entity.PostToChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostToChat, Long> {
}
