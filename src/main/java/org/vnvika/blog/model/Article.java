package org.vnvika.blog.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String text;

    @Enumerated(EnumType.STRING)
    private StatusArticle status;

    private Integer author_id;
    private Date created_at;
    private Date updated_at;

    public Article(String title, String text, StatusArticle status, Integer author_id) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.author_id = author_id;
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StatusArticle getStatus() {
        return status;
    }

    public void setStatus(StatusArticle status) {
        this.status = status;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
