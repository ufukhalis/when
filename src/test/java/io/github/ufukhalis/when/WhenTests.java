package io.github.ufukhalis.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WhenTests {

    @Test
    void whenConditionExecuted_thenReturnCorrectMatch() {
        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 20).thenReturn(i -> i + 2).execute();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(11, result.get());
    }

    @Test
    void whenMultipleConditionMatched_thenReturnFirstMatch() {
        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 10).thenReturn(i -> i + 2)
                .condition(i -> i == 20).thenReturn(i -> i + 3)
                .execute();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(11, result.get());
    }

    @Test
    void whenExecuteNotCalled_thenNoResultExpected() {
        Integer integer = 10;

        When.Return<Object, Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 20).thenReturn(i -> i + 2);

        Assertions.assertFalse(result.getClass().isAssignableFrom(Optional.class));
    }

    @Test
    void whenNoConditionMatched_thenReturnEmptyResult() {
        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 20).thenReturn(i -> i + 1)
                .condition(i -> i == 30).thenReturn(i -> i + 2).execute();

        Assertions.assertFalse(result.isPresent());
    }
}
