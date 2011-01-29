/*
 * Copyright © 2010-2011 Daniel Solano Gómez
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package demos.atomic;

import java.util.concurrent.TimeUnit;

public class AtomicDemo {
    private static final long NUM_TASKS = 48000000L;

    public static void main(String[] arguments) throws InterruptedException {
        final int numThreads = Integer.parseInt(arguments[0]);
        System.out.println("Using " + numThreads + " threads.");
        final Thread[] threads = new Thread[numThreads];
        final XorShiftRandom random = getRandom(arguments);
        for (int i = 0; i < numThreads; ++i) {
            threads[i] =
                    new Thread(new RandomTask(random, NUM_TASKS / numThreads));
        }
        final long startTime = System.nanoTime();
        for (int i = 0; i < numThreads; ++i) {
            threads[i].start();
        }
        for (int i = 0; i < numThreads; ++i) {
            threads[i].join();
        }
        final long endTime = System.nanoTime();
        final long duration = TimeUnit.MILLISECONDS
                .convert(endTime - startTime, TimeUnit.NANOSECONDS);
        System.out.println("Took " + duration + " millis");
    }

    private static XorShiftRandom getRandom(final String[] arguments) {
        for (final String argument : arguments) {
            if ("nolock".equals(argument)) {
                System.out.println("Using non-locking random.");
                return new AtomicXorShiftRandom();
            }
        }
        System.out.println("Using locking random.");
        return new LockingXorShiftRandom();
    }
}
