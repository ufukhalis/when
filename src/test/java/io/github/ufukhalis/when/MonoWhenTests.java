package io.github.ufukhalis.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(TimingExtension.class)
@Execution(ExecutionMode.CONCURRENT)
public class MonoWhenTests {

    @Test
    void whenConditionMatched_thenRunCorrectMapper() {
        System.out.println(Thread.currentThread().getName());

        Mono<Integer> result = When.of(Mono.just(10))
                .condition(i -> i == 10, i -> 1)
                .condition(i -> i == 20, i -> 2)
        .execute();

        StepVerifier.create(result)
                .consumeNextWith(value -> Assertions.assertEquals(1, value)).verifyComplete();
    }

    @Test
    void whenMultipleConditionMatched_thenRunLastMapper() {
        System.out.println(Thread.currentThread().getName());

        Mono<Integer> result = When.of(Mono.just(10))
                .condition(i -> i == 10, i -> i + 1)
                .condition(i -> i == 10, i -> i + 2)
                .condition(i -> i == 20, i -> i + 3)
                .execute();

        StepVerifier.create(result)
                .consumeNextWith(value -> Assertions.assertEquals(12, value)).verifyComplete();
    }

    @Test
    void whenNoConditionMatched_thenEmptyResult() {
        System.out.println(Thread.currentThread().getName());


        Mono<Integer> result = When.of(Mono.just(10))
                .condition(i -> i == 20, i -> i + 1)
                .condition(i -> i == 30, i -> i + 2)
                .execute();

        StepVerifier.create(result)
                .expectComplete().verify();
    }
}
