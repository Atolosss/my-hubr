package com.example.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    private Audit audit;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();
}

