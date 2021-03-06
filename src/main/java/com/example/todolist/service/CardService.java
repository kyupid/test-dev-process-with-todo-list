package com.example.todolist.service;

import com.example.todolist.domain.Actions;
import com.example.todolist.domain.Card;
import com.example.todolist.domain.Column;
import com.example.todolist.dto.CardAddRequestDTO;
import com.example.todolist.dto.CardUpdateRequestDto;
import com.example.todolist.exception.CardNotFoundException;
import com.example.todolist.exception.ColumnNotFoundException;
import com.example.todolist.exception.ColumnNotMatchException;
import com.example.todolist.repository.CardRepository;
import com.example.todolist.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;
    private final LogService logService;

    public void addCard(Long columnId, CardAddRequestDTO cardRequest) {
        Column column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);
        Card card = new Card(cardRequest.getTitle(), cardRequest.getContent(), cardRequest.getAuthor(), column);
        logService.createLog(card, Actions.ENROLL, column);
        cardRepository.save(card);
    }

    public Card updateCard(long columnId, long cardId, CardUpdateRequestDto cardUpdateRequestDto) {
        Column column = columnRepository.findById(columnId).orElseThrow(ColumnNotFoundException::new);
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);

        card.update(cardUpdateRequestDto.getTitle(), cardUpdateRequestDto.getContent(), column);

        return cardRepository.save(card);
    }

    public void delete(long columnId, long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);

        if (!card.hasSameColumnId(columnId)) {
            throw new ColumnNotMatchException();
        }

        cardRepository.delete(card);
    }
}
