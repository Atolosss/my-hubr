package com.example.service;

import com.example.constant.ErrorCode;
import com.example.exceptions.ServiceException;
import com.example.mapper.ChatMapper;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.entity.Post;
import com.example.model.enums.PostSortType;
import com.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ChatMapper chatMapper;

    public List<PostRs> getSortedPosts(final PostSortType sort) {
        final Comparator<Post> comparator = sort != null ? sort.getComparator() : PostSortType.ID.getComparator();
        return postRepository.findAll()
            .stream()
            .sorted(comparator)
            .map(chatMapper::toPostRs)
            .toList();
    }

    public List<PostRs> getPostsBetweenDates(final LocalDateTime startDate, final LocalDateTime endDate) {
        return postRepository.findAll()
            .stream()
            .filter(post -> post.getCreateDateTime().isAfter(startDate)
                && post.getCreateDateTime().isBefore(endDate))
            .map(chatMapper::toPostRs)
            .toList();
    }

    public PostRs getPost(final Long id, final boolean includeComments) {
        final Post post = postRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, id));

        final PostRs postRs = chatMapper.toPostRs(post);

        postRs.setComments(chatMapper.commentRsList(post.getComments()));
        return postRs;
    }

    public PostRs save(final AddPostRq addPost) {
        final Post post = chatMapper.toPost(addPost);
        postRepository.save(post);
        return chatMapper.toPostRs(post);
    }

    public void deleteById(final Long id) {
        postRepository.deleteById(id);
    }

}
