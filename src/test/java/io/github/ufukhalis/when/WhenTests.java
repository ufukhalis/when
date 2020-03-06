package io.github.ufukhalis.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Optional;

@Execution(ExecutionMode.CONCURRENT)
public class WhenTests {

    @Test
    void whenConditionExecuted_thenReturnCorrectMatch() {
        System.out.println(Thread.currentThread().getName());

        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 20).thenReturn(i -> i + 2).execute();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(11, result.get());
    }

    @Test
    void whenMultipleConditionMatched_thenReturnFirstMatch() {
        System.out.println(Thread.currentThread().getName());

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
        System.out.println(Thread.currentThread().getName());

        Integer integer = 10;

        When.Return<Object, Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 20).thenReturn(i -> i + 2);

        Assertions.assertFalse(result.getClass().isAssignableFrom(Optional.class));
    }

    @Test
    void whenNoConditionMatched_thenReturnEmptyResult() {
        System.out.println(Thread.currentThread().getName());

        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 20).thenReturn(i -> i + 1)
                .condition(i -> i == 30).thenReturn(i -> i + 2).execute();

        Assertions.assertFalse(result.isPresent());
    }
}
