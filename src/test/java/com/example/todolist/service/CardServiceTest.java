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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private Card card;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private Column column;

    @Mock
    private CardAddRequestDTO cardRequest;

    @Mock
    private CardUpdateRequestDto cardUpdateRequestDto;

    @InjectMocks
    private CardService cardService;

    @Mock
    private LogService logService;

    @Test
    @DisplayName("의존성 없는 단일 Repository 테스트")
    void testRepository() {
        //given
        Card card = mock(Card.class);
        //when
        cardRepository.save(card);
        //then
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("카드 추가 기능 테스트")
    void addCard() {
        Long columnId = 1L;

        // 간단한 반환 동작을 구성한다
        // 즉 columnRepository.findById(어떤숫자) 를 했을때 (when)
        // Optional<Column> 을 반환한다 (return)
        when(columnRepository.findById(anyLong())).thenReturn(Optional.of(column));

        // addCard 메소드는 columnId를 가지고 해당 id를 가진 column을 찾을 것이고
        // 그 column에 repository를 통해서 card를 save할것이다
        cardService.addCard(columnId, cardRequest);

        verify(logService, times(1)).createLog(any(Card.class), eq(Actions.ENROLL), any(Column.class));
        // cardRepository가 한번호출되었고 Card가 저장되었는지 verify한다
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("컬럼이 없을 시 ColumnNotFoundException 발생 테스트")
    void addCardColumnNotFoundException() {
        Long columnId = 1L;

        // 어떤 컬럼을 찾았을때 익셉션날리도록 설정하고.
        when(columnRepository.findById(anyLong())).thenThrow(ColumnNotFoundException.class);

        // assert한다. addCard할때  columnRepository.findById를 사용할것이므로 Exception을 날릴것이다. -> true
        Assertions.assertThrows(ColumnNotFoundException.class, () -> cardService.addCard(columnId, cardRequest));

        // 고로 save는 한번도 호출하지 않아야한다
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("카드 수정 기능 테스트")
    void updateCard() {
        when(columnRepository.findById(anyLong())).thenReturn(Optional.of(column));
        when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
        when(cardUpdateRequestDto.getTitle()).thenReturn("");
        when(cardUpdateRequestDto.getContent()).thenReturn("");

        cardService.updateCard(1L, 1L, cardUpdateRequestDto);

        verify(card, times(1)).update(anyString(), anyString(), any(Column.class));
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("카드가 없을 시 CardNotFoundException 발생")
    void throwExceptionIfCardNotPresent() {
        when(columnRepository.findById(anyLong())).thenReturn(Optional.of(column));
        when(cardRepository.findById(anyLong())).thenThrow(CardNotFoundException.class);

        assertThatThrownBy(() -> cardService.updateCard(1L, 1L, cardUpdateRequestDto)).isInstanceOf(CardNotFoundException.class);

        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("카드 삭제 기능 테스트")
    public void deleteCard() {
        when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
        when(card.hasSameColumnId(anyLong())).thenReturn(true);

        cardService.delete(1L, 1L);

        verify(cardRepository, times(1)).delete(any(Card.class));
    }

    @Test
    @DisplayName("컬럼 id와 카드의 컬럼 id가 다를때 ColumnNotMatchException 발생")
    void throwExceptionIfColumnNotMatch() {
        when(cardRepository.findById(anyLong())).thenReturn(Optional.of(card));
        when(card.hasSameColumnId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> cardService.delete(1L, 1L)).isInstanceOf(ColumnNotMatchException.class);

        verify(cardRepository, never()).delete(any(Card.class));

    }
}
