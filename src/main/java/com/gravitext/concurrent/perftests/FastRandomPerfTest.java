package com.gravitext.concurrent.perftests;

import com.gravitext.concurrent.ConcurrentTest;
import com.gravitext.concurrent.FastRandom;

public class FastRandomPerfTest implements ConcurrentTest
{
    public int runTest( int run, int seed )
    {
        FastRandom g = new FastRandom( seed + run );
        for( int i = 0; i < 10000; ++i ) g.nextInt(100);
        return ( ( g.nextInt() & 0x7fffffff ) % 3 );
    }
}