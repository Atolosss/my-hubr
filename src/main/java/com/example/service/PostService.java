package com.example.service;

import com.example.exceptions.ErrorCode;
import com.example.exceptions.ServiceException;
import com.example.mapper.ChatMapper;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import com.example.model.enums.PostSortType;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ChatMapper chatMapper;

    public List<PostRs> getSortedPosts(final PostSortType sort) {
        return chatMapper.postRsList(postRepository.findAll());
    }

    public List<PostRs> getPosts() {
        return chatMapper.postRsList(postRepository.findAll());
    }

    public List<PostRs> getPostsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return postRepository.findAll()
                .stream()
                .filter(post -> !post.getCreateDate().isBefore(startDate) &&
                        !post.getCreateDate().isAfter(endDate))
                .map(chatMapper::toPostRs)
                .collect(Collectors.toList());
    }

    public PostRs getPost(final Long id) {
        return postRepository.findById(id)
                .map(chatMapper::toPostRs)
                .orElseThrow(() -> new ServiceException(ErrorCode.ERROR_CODE_0001, id));
    }

    public PostRs save(final AddPostRq addPost) {
        Post post = chatMapper.toPost(addPost);
        postRepository.save(post);
        return chatMapper.toPostRs(post);
    }

    public void deleteById(final Long id) {
        postRepository.deleteById(id);
    }
}
