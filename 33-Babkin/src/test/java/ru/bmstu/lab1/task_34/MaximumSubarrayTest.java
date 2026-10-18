package ru.bmstu.lab1.task_34;

// Задача 34. Проверки максимальной суммы непустого непрерывного подмассива.

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class MaximumSubarrayTest {
    @Test
    void shouldFindBestContinuousSectionWithoutSkippingNegativeValues() {
        int[] values = {-2, 3, -1, 4, -5};

        assertEquals(6L, MaximumSubarray.maxSum(values));
        assertArrayEquals(new int[] {-2, 3, -1, 4, -5}, values);
    }

    @Test
    void shouldRestartAfterAnUnprofitablePrefix() {
        assertEquals(7L, MaximumSubarray.maxSum(new int[] {4, -10, 3, 4, -20}));
        assertEquals(9L, MaximumSubarray.maxSum(new int[] {-100, 2, 3, 4}));
        assertEquals(9L, MaximumSubarray.maxSum(new int[] {2, 3, 4, -100}));
    }

    @Test
    void shouldReturnLargestElementWhenAllValuesAreNegative() {
        assertEquals(-2L, MaximumSubarray.maxSum(new int[] {-5, -2, -8}));
        assertEquals(Integer.MIN_VALUE,
                MaximumSubarray.maxSum(new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE}));
    }

    @Test
    void shouldHandleZerosAndSingleElementsAsNonemptySubarrays() {
        assertEquals(0L, MaximumSubarray.maxSum(new int[] {0, 0, 0}));
        assertEquals(0L, MaximumSubarray.maxSum(new int[] {-3, 0, -4}));
        assertEquals(-7L, MaximumSubarray.maxSum(new int[] {-7}));
        assertEquals(7L, MaximumSubarray.maxSum(new int[] {7}));
        assertEquals(0L, MaximumSubarray.maxSum(new int[] {0}));
    }

    @Test
    void shouldAccumulateBeyondIntegerRange() {
        assertEquals(6_442_450_941L,
                MaximumSubarray.maxSum(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
        assertEquals(4_294_967_294L,
                MaximumSubarray.maxSum(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectEmptyAndNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> MaximumSubarray.maxSum(new int[0]));
        assertThrows(NullPointerException.class, () -> MaximumSubarray.maxSum(null));
    }

    @Test
    void shouldMatchBruteForceForSeededShortInputs() {
        Random random = new Random(3401L);
        for (int trial = 0; trial < 500; trial++) {
            int[] values = random.ints(1 + random.nextInt(25), -1_000, 1_001).toArray();
            int[] before = values.clone();

            assertEquals(bruteForceMaxSum(values), MaximumSubarray.maxSum(values), "trial=" + trial);
            assertArrayEquals(before, values, "Input was modified at trial=" + trial);
        }
    }

    @Test
    void shouldSumNinetyNineThousandNineHundredNinetyNineLargeValues() {
        int[] values = new int[99_999];
        Arrays.fill(values, Integer.MAX_VALUE);

        assertEquals(99_999L * Integer.MAX_VALUE, MaximumSubarray.maxSum(values));
    }

    private static long bruteForceMaxSum(int[] values) {
        long best = Long.MIN_VALUE;
        for (int start = 0; start < values.length; start++) {
            long sum = 0;
            for (int end = start; end < values.length; end++) {
                sum += values[end];
                best = Math.max(best, sum);
            }
        }
        return best;
    }
}
