package com.example.service;

import com.example.exceptions.ErrorCode;
import com.example.exceptions.ServiceException;
import com.example.mapper.PostMapper;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import com.example.model.enums.PostSortType;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<PostRs> getPosts(final PostSortType sort) {
        return postRepository.findAll()
                .stream()
                .sorted(sort.getComparator())
                .map(postMapper::mapToPostRs)
                .toList();
    }

    public PostRs getPost(final Long id) {
        return postRepository.findById(id)
                .map(postMapper::mapToPostRs)
                .orElseThrow(() -> new ServiceException(ErrorCode.ERROR_CODE_0001, id));
    }

    public PostRs save(final AddPostRq addPost) {
        Post post = postMapper.mapToPost(addPost);
        postRepository.save(post);
        return postMapper.mapToPostRs(post);
    }

    public void deleteById(final Long id) {
        postRepository.deleteById(id);
    }
}
