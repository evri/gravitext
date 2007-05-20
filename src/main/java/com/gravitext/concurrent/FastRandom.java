package com.gravitext.concurrent;

/**
 * A limited quality but high performance random number generator suitable for
 * performance testing. This class offers no thread safety and is best used
 * exclusively by each test thread. The performance advantage over
 * java.util.Random was found to be 34x for Sun JDK 1.5.0_09 and 29x for Sun JDK
 * 1.6.0-beta2 on Linux. The generation cost for java.util.Random is potentially
 * significant to some performance test cases.
 * 
 * @author David Kellum
 */
public final class FastRandom
{
    /**
     * Construct with FastRandom.generateSeed() as the seed.
     */    
    public FastRandom()
    {
        this( generateSeed() );
    }

    /**
     * Construct with the specified seed value.
     */
    public FastRandom( int seed )
    {
        _r = seed;
        nextInt(); // Avoid seed being returned in first call to nextInt(). 
    }
    
    /**
     * @return a psuedorandom value in the range [0,high).
     */
    public int nextInt( int high )
    {
        return nextInt( 0, high );
    }
    
    /**
     * @return a psuedorandom value in the range [low,high).
     */
    public int nextInt( int low, int high )
    {
        int n = ( nextInt() % ( high - low ) );
        if( n < 0 ) n = -n;
        return ( n + low );
    }
    
    /**
     * @return a psuedorandom integer using all 32-bits.
     */
    public int nextInt()
    {
        // Based on: George Marsaglia, XorShift RNGs, Journal of
        // Statistical Software 8(13), 2003
        int r = _r;
        _r = r ^ ( r << 5 ) ^ ( r >>> 9 ) ^ ( r << 7 );
        return r;
    }
    
    /**
     * Generate a new seed derived from System.nanoTime() 
     * and a new Object.hashCode().  This is a relatively slow operation.
     */
    public static int generateSeed()
    {
        long t = System.nanoTime();
        return (int) ( t >>> 32 ) ^ (int) t ^ ( new Object() ).hashCode();
    }

    private int _r;
}
