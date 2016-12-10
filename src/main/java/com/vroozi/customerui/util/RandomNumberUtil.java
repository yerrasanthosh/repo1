package com.vroozi.customerui.util;

import java.util.Random;

/**
 * @author Muhammad Haris
 *
 */
public class RandomNumberUtil {

  /**
   * Generates a 9-Digit random integer with the help of {@link #generateRandomInteger(int)}
   * 
   * @return a 9-digit random integer
   * @author Muhammad Haris
   */
  public static int generateNineDigitRandomInteger() {
    return generateRandomInteger(1000000000);
  }

  /**
   * Generates a random integer using pseudo-random number generator (PRNG).
   * <p>
   * See {@link Random} for details.
   * 
   * @param upperBound - number to limit the random number range
   * @return a random integer
   * @author Muhammad Haris
   */
  public static int generateRandomInteger(int upperBound) {
    /*
     * Use default constructor which sets the seed of the random number generator to a value very
     * likely to be distinct from any other invocation of this constructor
     */
    Random random = new Random();

    /* Get uniformly distributed int value between 0 (inclusive) and the upper bound (exclusive) */
    return random.nextInt(upperBound);
  }
}
