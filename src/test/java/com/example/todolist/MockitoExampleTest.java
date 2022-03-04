package com.example.todolist;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class MockitoExampleTest {
    private List<String> mock = mock(List.class);

    @Test
    public void verifyMethod_basic() {
        String value = mock.get(0);
        String value2 = mock.get(1);

        // 그냥 mock이라서 null인듯
        System.out.println(value);
        System.out.println(value2);

        // mock 뒤에 get(인자) 를 호출했냐는 말임
        verify(mock).get(0);

        // 해당 mock이 times(인자) 인자만큼 호출이되었는지 검증
        verify(mock, times(2)).get(anyInt());

        // 해당 mock이 최소 atLeast(인자) 인자만큼 호출이되었는지 검증
        verify(mock, atLeast(1)).get(anyInt());

        // 최소한번 호출됐는지 검증
        verify(mock, atLeastOnce()).get(anyInt());

        // 해당 mock 이 정해진 횟수보다 적게 호출됐는지 검증 2아래 숫자만큼
        verify(mock, atMost(2)).get(anyInt());

        // 해당 mock이 아예 호출이 안됐는지 검증
        verify(mock, never()).get(2);
    }
}
