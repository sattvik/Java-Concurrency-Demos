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

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    private static final int FIXED_THREAD_COUNT = 5;
    private static final int NUM_TASKS = 100;

    public static void main(final String[] arguments)
            throws ExecutionException, InterruptedException {
        final ExecutorService executor = initExecutor(arguments);
        final CompletionService<String> completionService =
                new ExecutorCompletionService<String>(executor);
        submitTasks(completionService);
        processResults(completionService);
        executor.shutdown();
    }

    /**
     * Gets an executor based on the arguments given.
     *
     * @param arguments the command line arguments
     *
     * @return an executor of the requested type
     */
    private static ExecutorService initExecutor(final String[] arguments) {
        if (arguments.length < 1) {
            throw new IllegalArgumentException("No arguments supplied.");
        }
        final String executorType = arguments[0];
        final ExecutorService executor;
        if (executorType.equals("fixed")) {
            executor = Executors.newFixedThreadPool(FIXED_THREAD_COUNT);
        } else if (executorType.equals("cached")) {
            executor = Executors.newCachedThreadPool();
        } else if (executorType.equals("single")) {
            executor = Executors.newSingleThreadExecutor();
        } else {
            throw new IllegalArgumentException("Executor type not recognized.");
        }
        return executor;
    }

    /**
     * Submits tasks to the completion service.
     *
     * @param completionService the service that will handle the tasks
     */
    private static void submitTasks(
            final CompletionService<String> completionService) {
        for (int i = 0; i < NUM_TASKS; ++i) {
            completionService.submit(new DemoTask(i));
        }
    }

    /**
     * Submits tasks to the completion service.
     *
     * @param completionService the service that will handle the tasks
     *
     * @throws InterruptedException if something bad happens
     * @throws ExecutionException   if something bad happens
     */
    private static void processResults(
            final CompletionService<String> completionService)
            throws InterruptedException, ExecutionException {
        for (int i = 0; i < NUM_TASKS; ++i) {
            final String result = completionService.take().get();
            System.out.println(result);
        }
    }
}
