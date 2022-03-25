package com.example.todolist.service;

import com.example.todolist.domain.Actions;
import com.example.todolist.domain.Card;
import com.example.todolist.domain.Column;
import com.example.todolist.domain.Log;
import com.example.todolist.repository.LogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    @InjectMocks
    private LogService logService;

    @Mock
    private Log log;

    @Mock
    private Card card;

    @Mock
    private LogRepository logRepository;

    @Mock
    private Actions action;

    @Mock
    private Column column;

    @Test
    @DisplayName("로그 추가 기능 테스트")
    void createLog() {
        logService.createLog(card, action, column);

        verify(logRepository, times(1)).save(any(Log.class));
    }
}
