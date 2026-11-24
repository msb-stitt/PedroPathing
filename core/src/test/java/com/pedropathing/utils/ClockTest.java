/*
 * Copyright (c) 2026 Pedro Pathing
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.pedropathing.utils;

import static com.google.common.truth.Truth.assertThat;

import com.pedropathing.controllers.PIDController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ClockTest {
    private final long[] nanos = {1_000_000_000L};

    @AfterEach
    void systemTimeAgain() {
        Clock.useSystemTime();
    }

    @Test
    void aTimerReadsTheClockSet() {
        Clock.use(() -> nanos[0]);
        Timer timer = new Timer();
        nanos[0] += 37_000_000L;
        assertThat(timer.milliseconds()).isEqualTo(37.0);
        assertThat(timer.milliseconds()).isEqualTo(37.0);
    }

    @Test
    void aPidIntegratesAndDifferentiatesOverTheClockSet() {
        Clock.use(() -> nanos[0]);
        PIDController ki = new PIDController(0, 1, 0);
        nanos[0] += 500_000_000L;
        assertThat(ki.calculate(0, 2)).isWithin(1e-12).of(1.0);
        PIDController kd = new PIDController(0, 0, 1);
        nanos[0] += 10_000_000L;
        kd.calculate(0, 0);
        nanos[0] += 10_000_000L;
        assertThat(kd.calculate(0, 1)).isWithin(1e-9).of(100.0);
    }

    @Test
    void useSystemTimeReadsSystemNanoTimeAgain() {
        Clock.use(() -> nanos[0]);
        Clock.useSystemTime();
        long before = System.nanoTime();
        long now = Clock.nanoTime();
        assertThat(now - before).isAtLeast(0L);
        assertThat(now - before).isLessThan(1_000_000_000L);
    }
}
