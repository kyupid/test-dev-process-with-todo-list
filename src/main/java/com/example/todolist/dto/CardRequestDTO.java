package com.example.todolist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDTO {

    private String title;
    private String content;
    private String author;
}
