package ru.bmstu.lab1.task_38;

// Задача 38. Проверки циклического сдвига влево тройным разворотом.

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

class LeftRotationTest {
    @Test
    void shouldRotateLeftInTheOriginalArray() {
        int[] values = {1, 2, 3, 4, 5};

        LeftRotation.rotate(values, 2);

        assertArrayEquals(new int[] {3, 4, 5, 1, 2}, values);
    }

    @Test
    void shouldNormalizeBoundaryDistancesIncludingLongMaximum() {
        int[] original = {Integer.MIN_VALUE, -2, 0, 4, Integer.MAX_VALUE};
        long[] distances = {0L, 1L, 4L, 5L, 6L, 10L, Long.MAX_VALUE};
        for (long distance : distances) {
            int[] actual = original.clone();

            LeftRotation.rotate(actual, distance);

            assertArrayEquals(rotatedCopy(original, distance), actual, "distance=" + distance);
        }
    }

    @Test
    void shouldSupportEmptyAndSingleElementArrays() {
        int[] empty = new int[0];
        int[] single = {Integer.MIN_VALUE};

        LeftRotation.rotate(empty, 0);
        LeftRotation.rotate(empty, Long.MAX_VALUE);
        LeftRotation.rotate(single, Long.MAX_VALUE);

        assertArrayEquals(new int[0], empty);
        assertArrayEquals(new int[] {Integer.MIN_VALUE}, single);
    }

    @Test
    void shouldRejectNegativeDistancesWithoutChangingInput() {
        int[] values = {1, 2, 3};

        assertThrows(IllegalArgumentException.class, () -> LeftRotation.rotate(values, -1));
        assertThrows(IllegalArgumentException.class, () -> LeftRotation.rotate(values, Long.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> LeftRotation.rotate(new int[0], -1));
        assertArrayEquals(new int[] {1, 2, 3}, values);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> LeftRotation.rotate(null, 0));
    }

    @Test
    void shouldMatchIndexedReferenceForSeededInputs() {
        Random random = new Random(3801L);
        for (int trial = 0; trial < 300; trial++) {
            int[] original = random.ints(random.nextInt(100), -20, 21).toArray();
            long distance = random.nextLong() & Long.MAX_VALUE;
            int[] actual = original.clone();

            LeftRotation.rotate(actual, distance);

            assertArrayEquals(rotatedCopy(original, distance), actual, "trial=" + trial);
        }
    }

    @Test
    void shouldRotateNinetyNineThousandNineHundredNinetyNineValues() {
        int[] original = new int[99_999];
        for (int index = 0; index < original.length; index++) {
            original[index] = index - 50_000;
        }
        int[] actual = original.clone();

        LeftRotation.rotate(actual, Long.MAX_VALUE);

        assertArrayEquals(rotatedCopy(original, Long.MAX_VALUE), actual);
    }

    private static int[] rotatedCopy(int[] values, long distance) {
        int[] result = new int[values.length];
        if (values.length == 0) {
            return result;
        }
        int shift = (int) (distance % values.length);
        for (int index = 0; index < values.length; index++) {
            result[index] = values[(index + shift) % values.length];
        }
        return result;
    }
}
