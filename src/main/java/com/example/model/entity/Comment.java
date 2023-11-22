package com.example.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    private String value;

    private Audit audit;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;

}
