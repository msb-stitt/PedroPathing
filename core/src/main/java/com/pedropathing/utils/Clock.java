/*
 * Copyright (c) 2026 Pedro Pathing
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.pedropathing.utils;

import java.util.function.LongSupplier;

/**
 * This is the Clock class. It is the time Pedro Pathing reads, in nanoseconds: System.nanoTime()
 * on a robot, or a simulation's own time, so a simulation run faster than real time, or stopped
 * in a debugger, gives the follower, its controllers and its timers simulated time.
 */
public final class Clock {
    private static volatile LongSupplier source = System::nanoTime;

    private Clock() {}

    /** This returns the current time in nanoseconds */
    public static long nanoTime() {
        return source.getAsLong();
    }

    /**
     * This makes Pedro Pathing read the time from the given source from now on
     * @param nanos the time in nanoseconds, such as a simulation's clock
     */
    public static void use(LongSupplier nanos) {
        source = nanos;
    }

    /** This makes Pedro Pathing read System.nanoTime() again, as on a robot */
    public static void useSystemTime() {
        source = System::nanoTime;
    }
}
