package org.vnvika.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String text;

    @Enumerated(EnumType.STRING)
    private StatusArticle status;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @Column( name = "created_at")
    private LocalDate createdAt;
    @Column( name = "updated_at")
    private LocalDate updatedAt;
}
