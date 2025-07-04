package com.example.yemek_tarifi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name= "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch =  FetchType.LAZY)
    private Recipe recipe;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    private int likeCount = 0;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new java.util.Date();
    }

}
