package com.example.service;

import com.example.exceptions.ServiceException;
import com.example.model.dto.AddPostRq;
import com.example.model.dto.PostRs;
import com.example.model.enums.PostSortType;
import com.example.constant.ErrorCode;
import com.example.mapper.PostMapper;
import com.example.model.entity.Post;
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
    private final PostMapper postMapper;

    public List<PostRs> getSortedPosts(final PostSortType sort) {
        final Comparator<Post> comparator = sort != null ? sort.getComparator() : PostSortType.ID.getComparator();
        return postRepository.fetchAllPosts()
            .stream()
            .sorted(comparator)
            .map(postMapper::toPostRs)
            .toList();
    }

    public List<PostRs> getPostsBetweenDates(final LocalDateTime startDate, final LocalDateTime endDate) {
        return postRepository.findAll()
            .stream()
            .filter(post -> post.getCreateDateTime().isAfter(startDate)
                && post.getCreateDateTime().isBefore(endDate))
            .map(postMapper::toPostRs)
            .toList();
    }

    public PostRs getPost(final Long id, final boolean includeComments) {
        final Post post = postRepository.findById(id)
            .orElseThrow(() -> new ServiceException(ErrorCode.ERR_CODE_001, id));

        return postMapper.toPostRs(post);
    }

    public PostRs save(final AddPostRq addPost) {
        final Post post = postMapper.toPost(addPost);
        postRepository.save(post);
        return postMapper.toPostRs(post);
    }

    public void deleteById(final Long id) {
        postRepository.deleteById(id);
    }

}
