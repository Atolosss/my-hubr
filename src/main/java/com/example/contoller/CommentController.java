package com.example.contoller;

import com.example.model.dto.AddCommentRq;
import com.example.model.dto.CommentRs;
import com.example.model.enums.CommentSortType;
import com.example.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping()
    public List<CommentRs> getAll(@RequestParam(required = false) final CommentSortType sort) {
        if(sort!=null) {
            return commentService.findAll(sort);
        }
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentRs get(@PathVariable final Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public CommentRs add(@RequestBody final AddCommentRq request) {
        return commentService.save(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        commentService.deleteById(id);
    }

}
