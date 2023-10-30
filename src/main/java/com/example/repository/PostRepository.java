package com.example.repository;

import com.example.model.entity.PostToChat;
import com.example.model.enums.PostSortType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostToChat, Long> {
}
