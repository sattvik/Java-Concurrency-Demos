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
package demos.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentModificationDemo {
    private static final int NUM_TASKS = 5;
    private static final int NUM_THREADS = 4;

    public static void main(final String[] arguments)
            throws InterruptedException {
        final ExecutorService executorService =
                Executors.newFixedThreadPool(NUM_THREADS);

        // create test collection
        final Collection<Integer> testCollection = new ArrayList<Integer>();
        for (int i = 0; i < 5; ++i) {
            testCollection.add(new Random().nextInt(10));
        }

        // create tasks
        final Set<Callable<String>> tasks = new HashSet<Callable<String>>();
        tasks.addAll(createIteratorTasks(testCollection));
        tasks.addAll(createAdderTasks(testCollection));
        tasks.addAll(createRemoverTasks(testCollection));

        // run tasks
        final List<Future<String>> futures = executorService.invokeAll(tasks);
        for (final Future<String> future : futures) {
            try {
                System.out.println(future.get());
            } catch (ExecutionException e) {
                e.getCause().printStackTrace();
            }
        }

        // wait for everything to finish
        executorService.shutdown();
    }

    private static Collection<Callable<String>> createIteratorTasks(
            final Collection<Integer> collection) {
        final List<Callable<String>> myTasks =
                new ArrayList<Callable<String>>(NUM_TASKS);
        for (int i = 0; i < NUM_TASKS; ++i) {
            myTasks.add(new IteratorTask(collection, i));
        }
        return myTasks;
    }

    private static Collection<Callable<String>> createAdderTasks(
            final Collection<Integer> collection) {
        final List<Callable<String>> myTasks =
                new ArrayList<Callable<String>>(NUM_TASKS);
        for (int i = 0; i < NUM_TASKS; ++i) {
            myTasks.add(new AdderTask(collection, i));
        }
        return myTasks;
    }

    private static Collection<Callable<String>> createRemoverTasks(
            final Collection<Integer> collection) {
        final List<Callable<String>> myTasks =
                new ArrayList<Callable<String>>(NUM_TASKS);
        for (int i = 0; i < NUM_TASKS; ++i) {
            myTasks.add(new RemoverTask(collection, i));
        }
        return myTasks;
    }
}
