package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWithoutCommentsRs {
    private Long id;

    private String name;

    private String description;

    private List<CommentRs> comments = new ArrayList<>();
}
