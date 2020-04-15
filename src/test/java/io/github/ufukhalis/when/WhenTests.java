package io.github.ufukhalis.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Optional;

@ExtendWith(TimingExtension.class)
@Execution(ExecutionMode.CONCURRENT)
public class WhenTests {

    @Test
    void whenConditionExecuted_thenReturnCorrectMatch() {
        System.out.println(Thread.currentThread().getName());

        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10, i -> i + 1)
                .condition(i -> i == 20, i -> i + 2).toOptional();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(11, result.get());
    }

    @Test
    void whenMultipleConditionMatched_thenReturnLastMatch() {
        System.out.println(Thread.currentThread().getName());

        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10, i -> i + 1)
                .condition(i -> i == 10, i -> i + 2)
                .condition(i -> i == 20, i -> i + 3)
                .toOptional();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(12, result.get());
    }

    @Test
    void whenNoConditionMatched_thenReturnEmptyResult() {
        System.out.println(Thread.currentThread().getName());

        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 20, i -> i + 1)
                .condition(i -> i == 30, i -> i + 2).toOptional();

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void whenThereAreManyCondition_thenReturnMatch() {
        Integer integer = 10;

        Optional<Integer> result = When.of(integer)
                .condition(i -> i == 30, i -> i + 1)
                .condition(i -> i == 30, i -> i + 2)
                .condition(i -> i == 30, i -> i + 2)
                .condition(i -> i == 30, i -> i + 2)
                .condition(i -> i == 30, i -> i + 2)
                .condition(i -> i == 10, i -> i + 2)
                .toOptional();

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void whenNoMatch_thenOrElseGetShouldWork() {
        Integer integer = 10;

        Integer result = When.of(integer)
                .condition(i -> i == 11, i -> i + 1)
                .condition(i -> i == 12, i -> i + 1)
                .condition(i -> i == 13, i -> i + 1)
                .condition(i -> i == 14, i -> i + 1)
                .condition(i -> i == 15, i -> i + 1)
                .getOrElseGet(() -> 10);

        Assertions.assertEquals(10, result);
    }

    @Test
    void whenNoMatch_thenOrElseShouldWork() {
        Integer integer = 10;

        Integer result = When.of(integer)
                .condition(i -> i == 11, i -> i + 1)
                .condition(i -> i == 12, i -> i + 1)
                .condition(i -> i == 13, i -> i + 1)
                .condition(i -> i == 14, i -> i + 1)
                .condition(i -> i == 15, i -> i + 1)
                .getOrElse(10);

        Assertions.assertEquals(10, result);
    }

    @Test
    void whenOptionalPassAndMatched_thenReturnMatch() {
        Optional<Integer> result = When.of(Optional.of(10))
                .condition(i -> i == 12, i -> i + 1)
                .condition(i -> i == 11, i -> i + 1)
                .condition(i -> i == 10, i -> i + 1)
                .toOptional();

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(11, result.get());
    }

    @Test
    void whenOptionalNullPassAndNotMatched_thenReturnEmpty() {
        Optional<Integer> result = When.of(Optional.<Integer>empty())
                .condition(i -> i == 12, i -> i + 1)
                .condition(i -> i == 11, i -> i + 1)
                .condition(i -> i == 10, i -> i + 1)
                .toOptional();

        Assertions.assertFalse(result.isPresent());
    }

}
