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

/**
 * A medium-quality random number generator implementing Marsaglia’s XORShift
 * RNG algorithm.  This is a thread-safe implementation the algorithm.
 */
public abstract class XorShiftRandom {
    /**
     * Implementation of a 64-bit XORShift algorithm.
     *
     * @param seed the seed value for the algorithm
     *
     * @return the next random value given the seed
     */
    protected static long xorShift(final long seed) {
        long nextVal = seed;
        nextVal ^= (nextVal << 21);
        nextVal ^= (nextVal >>> 35);
        nextVal ^= (nextVal << 4);
        return nextVal;
    }

    /**
     * Provides a thread-safe method of getting the next random value and
     * updating the seed.
     *
     * @return the next 64-bit random value as a {@code long}
     */
    public abstract long nextRandom();
}
