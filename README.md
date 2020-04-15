[![Build Status](https://travis-ci.org/ufukhalis/when.svg?branch=master)](https://travis-ci.org/ufukhalis/when)
[![Coverage Status](https://coveralls.io/repos/github/ufukhalis/when/badge.svg?branch=master)](https://coveralls.io/github/ufukhalis/when?branch=master)

WHEN
=======

`WHEN` is a simple matching library which uses Java 8+ in the background and
doesn't have any other dependency on other libs.


How to Use
-------------

Firstly, you should add latest `WHEN` dependency to your project.

```$xslt
<dependency>
    <groupId>io.github.ufukhalis</groupId>
    <artifactId>when</artifactId>
    <version>0.0.5</version>
</dependency>
```

Then, we can easily build when conditions like below. `WHEN` won't work until you call the `toOptional`
method.

```$xslt

Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 20).thenReturn(i -> i + 2)
                .toOptional();

```

If there is no match the optional value will be empty.

You can also pass `Optional`.

```$xslt

Optional<Integer> result = When.of(Optional.of(10))
                .condition(i -> i == 12).thenReturn(i -> i + 1)
                .condition(i -> i == 11).thenReturn(i -> i + 1)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .toOptional();

```

And also you can use other methods to trigger pipeline such as `getOrElse` or `getOrElseGet`.

```$xslt

Integer result = When.of(integer)
                .condition(i -> i == 11).thenReturn(i -> i + 1)
                .condition(i -> i == 12).thenReturn(i -> i + 1)
                .condition(i -> i == 13).thenReturn(i -> i + 1)
                .getOrElseGet(() -> 10);

```

```$xslt

Integer result = When.of(integer)
                .condition(i -> i == 11).thenReturn(i -> i + 1)
                .condition(i -> i == 12).thenReturn(i -> i + 1)
                .condition(i -> i == 13).thenReturn(i -> i + 1)
                .getOrElse(10);

```

`When` project has also reactor `Mono` type support. You can use `MonoWhen` type to create
multiple conditions.

```$xslt

Mono<Integer> result = MonoWhen.of(Mono.just(10))
                .condition(i -> i == 10, i -> 1)
                .condition(i -> i == 20, i -> 2)
        .execute();

```

Important Note : If there are multiple match for `MonoWhen`, it will return the last match. 
But it won't execute previous matches.

License
------------
All code in this repository is licensed under the Apache License, Version 2.0. See [LICENCE](./LICENSE).