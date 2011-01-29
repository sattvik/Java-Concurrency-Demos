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
package demos.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/** Demo to show the abilities of a scheduled executor. */
public class ScheduledExecutorDemo {
    private static final int CORE_POOL_SIZE = 2;

    public static void main(final String[] arguments)
            throws ExecutionException, InterruptedException {
        final ScheduledExecutorService executorService =
                Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        // schedule a heartbeat every second
        executorService.scheduleAtFixedRate(new HeartBeatTask(), 1L, 1L,
                                            TimeUnit.SECONDS);
        // schedule a future task
        final ScheduledFuture<String> future = executorService
                .schedule(new DemoTask(42), 5L, TimeUnit.SECONDS);
        // wait for the future task to complete
        System.out.println(future.get());
        executorService.shutdown();
    }

    private static final class HeartBeatTask implements Runnable {
        @Override
        public void run() {
            System.out.println("THUB-DUB!");
        }
    }
}
