package com.vroozi.customerui.util;

import static com.vroozi.customerui.util.RandomNumberUtil.generateNineDigitRandomInteger;
import static com.vroozi.customerui.util.RandomNumberUtil.generateRandomInteger;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Muhammad Haris
 *
 */
public class RandomNumberUtilTest {

  @Test
  public void generateRandomIntegerShouldReturnUniqueIntegerEachTime() {
    // Given
    Set<Integer> randomNumbers = new HashSet<>();
    int randomNumbersCount = 100;

    // When
    for (int i = 0; i < randomNumbersCount; i++) {
      randomNumbers.add(generateRandomInteger(1000000000));
    }

    // Then
    assertThat(randomNumbers.size(), equalTo(randomNumbersCount));
  }

  @Test
  public void generateNineDigitRandomIntegerShouldReturnLessThanTenDigitInteger() {
    // When
    int number = generateNineDigitRandomInteger();
    // Then
    assertThat(number, lessThan(1000000000));
  }

  @Test
  public void generateNineDigitRandomIntegerShouldNotReturnGreaterThanOrEqualToTenDigitInteger() {
    // When
    int number = generateNineDigitRandomInteger();
    // Then
    assertThat(number, not(greaterThanOrEqualTo(1000000000)));
  }


}
