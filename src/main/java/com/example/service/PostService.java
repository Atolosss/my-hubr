package com.example.service;

import com.example.exceptions.ServiceException;
import com.example.mapper.ChatMapper;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.PostToChat;
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
        return postRepository.findAll()
                .stream()
                .sorted(sort.getComparator())
                .map(chatMapper::toPostRs)
                .collect(Collectors.toList());
    }

    public List<PostRs> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(chatMapper::toPostRs)
                .toList();
    }

    public List<PostRs> getPostsBetweenDates(final LocalDate startDate, final LocalDate endDate) {
        return postRepository.findAll()
                .stream()
                .filter(post -> !post.getCreateDate().isBefore(startDate)
                        && !post.getCreateDate().isAfter(endDate))
                .map(chatMapper::toPostRs)
                .collect(Collectors.toList());
    }

    public PostRs getPost(final Long id) {
        return postRepository.findById(id)
                .map(chatMapper::toPostRs)
                .orElseThrow(() -> new ServiceException("Post not found exception with id"));
    }

    public PostRs save(final AddPostRq addPost) {
        final PostToChat postToChat = chatMapper.toPost(addPost);
        postRepository.save(postToChat);
        return chatMapper.toPostRs(postToChat);
    }

    public void deleteById(final Long id) {
        postRepository.deleteById(id);
    }

    public PostRs getPostWithComments(final Long id) {
        final PostToChat postToChat = postRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Comment not found exception with id"));

        final PostRs postRs = chatMapper.toPostRs(postToChat);

        postRs.setComments(chatMapper.commentRsList(postToChat.getComments()));
        return postRs;
    }
}
