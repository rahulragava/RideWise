package com.airtribe.ridewise.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {

    private static final AtomicLong counter = new AtomicLong(0);

    private IdGenerator() {
    }

    public static String generate(String prefix) {
        return prefix + "-" + counter.incrementAndGet();
    }
}
