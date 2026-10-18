package ru.bmstu.lab1.task_19;

// Задача 19. Проверки слияния двух неубывающих массивов за O(n + m).

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SortedMergeTest {
    @Test
    void shouldMergeInterleavedValuesAndPreserveDuplicates() {
        int[] first = {1, 4, 4, 7};
        int[] second = {2, 4, 8};

        int[] actual = SortedMerge.merge(first, second);

        assertArrayEquals(new int[] {1, 2, 4, 4, 4, 7, 8}, actual);
        assertArrayEquals(new int[] {1, 4, 4, 7}, first);
        assertArrayEquals(new int[] {2, 4, 8}, second);
        assertNotSame(first, actual);
        assertNotSame(second, actual);
    }

    @Test
    void shouldAppendEitherRemainingTail() {
        assertArrayEquals(new int[] {-5, -2, 0, 3, 8},
                SortedMerge.merge(new int[] {-5, -2}, new int[] {0, 3, 8}));
        assertArrayEquals(new int[] {-5, -2, 0, 3, 8},
                SortedMerge.merge(new int[] {0, 3, 8}, new int[] {-5, -2}));
    }

    @Test
    void shouldSupportEmptyInputsAndReturnIndependentArray() {
        int[] values = {-1, 0, 2};
        int[] mergedFirst = SortedMerge.merge(values, new int[0]);
        int[] mergedSecond = SortedMerge.merge(new int[0], values);

        assertArrayEquals(values, mergedFirst);
        assertArrayEquals(values, mergedSecond);
        assertNotSame(values, mergedFirst);
        assertNotSame(values, mergedSecond);
        assertArrayEquals(new int[0], SortedMerge.merge(new int[0], new int[0]));
    }

    @Test
    void shouldCompareExtremeIntegersWithoutOverflow() {
        int[] first = {Integer.MIN_VALUE, -1, Integer.MAX_VALUE};
        int[] second = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};

        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE, -1, 0,
                Integer.MAX_VALUE, Integer.MAX_VALUE}, SortedMerge.merge(first, second));
    }

    @Test
    void shouldRejectEitherNullInput() {
        assertThrows(NullPointerException.class, () -> SortedMerge.merge(null, new int[0]));
        assertThrows(NullPointerException.class, () -> SortedMerge.merge(new int[0], null));
    }

    @Test
    void shouldMatchIndependentSortingOracleForSeededInputs() {
        Random random = new Random(1901L);
        for (int trial = 0; trial < 250; trial++) {
            int[] first = random.ints(random.nextInt(65), -20, 21).toArray();
            int[] second = random.ints(random.nextInt(65), -20, 21).toArray();
            Arrays.sort(first);
            Arrays.sort(second);
            int[] firstBefore = first.clone();
            int[] secondBefore = second.clone();
            int[] expected = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, expected, first.length, second.length);
            Arrays.sort(expected);

            assertArrayEquals(expected, SortedMerge.merge(first, second), "trial=" + trial);
            assertArrayEquals(firstBefore, first, "First input was modified at trial=" + trial);
            assertArrayEquals(secondBefore, second, "Second input was modified at trial=" + trial);
        }
    }

    @Test
    void shouldMergeNinetyNineThousandNineHundredNinetyNineValues() {
        int[] first = new int[50_000];
        int[] second = new int[49_999];
        int[] expected = new int[99_999];
        for (int index = 0; index < first.length; index++) {
            first[index] = 2 * index;
        }
        for (int index = 0; index < second.length; index++) {
            second[index] = 2 * index + 1;
        }
        for (int index = 0; index < expected.length; index++) {
            expected[index] = index;
        }

        assertArrayEquals(expected, SortedMerge.merge(first, second));
    }
}
