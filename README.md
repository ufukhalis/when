[![Build Status](https://travis-ci.org/ufukhalis/when.svg?branch=master)](https://travis-ci.org/ufukhalis/when)
[![Coverage Status](https://coveralls.io/repos/github/ufukhalis/when/badge.svg?branch=master)](https://coveralls.io/github/ufukhalis/when?branch=master)

WHEN
=======

`WHEN` is a simple pattern matching library which uses Java 8+ in the background and
does'nt have any other dependency to other libs.


How to Use

Firstly, you should add latest `WHEN` dependency to your project.

```$xslt
<dependency>
    <groupId>io.github.ufukhalis</groupId>
    <artifactId>when</artifactId>
    <version>0.0.1</version>
</dependency>
```

Then, we can easily build when conditions like below. `WHEN` won't work until you call the `execute`
method.

```$xslt

Optional<Integer> result = When.of(integer)
                .condition(i -> i == 10).thenReturn(i -> i + 1)
                .condition(i -> i == 20).thenReturn(i -> i + 2)
                .execute();

```

If there is no match the optional value will be empty.

License
------------
All code in this repository is licensed under the Apache License, Version 2.0. See [LICENCE](./LICENSE).