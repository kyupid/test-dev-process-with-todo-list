# test-dev-process-with-todo-list
테스트 개발 해보기

## 목표

- TDD 이전에 T는 무엇인가
- 따라해보며 테스트가 대충 이런거다 느껴보기
- JUnit & Mockito 대략적으로 익히기

## 의존성
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	runtimeOnly 'com.h2database:h2'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

## Mockito

### when/then/verify 이해하기

```java
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
```

레퍼런스: https://github.com/kimnayeon0108/Todo-list
