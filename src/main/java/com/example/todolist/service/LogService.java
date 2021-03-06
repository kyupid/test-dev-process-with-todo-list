package com.example.todolist.service;

import com.example.todolist.domain.Actions;
import com.example.todolist.domain.Card;
import com.example.todolist.domain.Column;
import com.example.todolist.domain.Log;
import com.example.todolist.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void createLog(Card card, Actions action, Column toColumn) {
        Log log = new Log(card.getAuthor(), action, toColumn, card.getColumn(), card);
        logRepository.save(log);
    }
}
