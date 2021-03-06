package com.example.todolist.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Card extends Core {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Column column;

    protected Card() {

    }

    public Card(String title, String content, String author, Column column) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.column = column;
    }

    public void update(String title, String content, Column column) {
        this.title = title;
        this.content = content;
        this.column = column;
    }

    public boolean hasSameColumnId(Long columnId) {
        return this.column.getId().equals(columnId);
    }
}
