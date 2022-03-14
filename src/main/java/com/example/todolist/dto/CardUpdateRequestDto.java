package com.example.todolist.dto;

public class CardUpdateRequestDto {

    private String title;
    private String content;
    private Long toColumId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getToColumId() {
        return toColumId;
    }

    public void setToColumId(Long toColumId) {
        this.toColumId = toColumId;
    }
}
