package ru.bmstu.lab1.task_8;

// Задача 8. Проверки устойчивого разделения элементов по знаку.

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SignFilterTest {
    @Test
    void shouldPreserveOrderAndExcludeZeros() {
        int[] values = {0, 5, -2, 5, 0, -9, 3};
        SignGroups groups = SignFilter.split(values);
        assertArrayEquals(new int[] {5, 5, 3}, groups.positive());
        assertArrayEquals(new int[] {-2, -9}, groups.negative());
        assertArrayEquals(new int[] {0, 5, -2, 5, 0, -9, 3}, values);
    }

    @Test
    void shouldAcceptEmptyZeroOnlyAndSingleSignArrays() {
        assertGroups(new int[0], new int[0], new int[0]);
        assertGroups(new int[] {0, 0}, new int[0], new int[0]);
        assertGroups(new int[] {1, 2}, new int[] {1, 2}, new int[0]);
        assertGroups(new int[] {-1, -2}, new int[0], new int[] {-1, -2});
    }

    @Test
    void shouldHandleIntegerLimitsAndRejectNull() {
        assertGroups(new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
                new int[] {Integer.MAX_VALUE}, new int[] {Integer.MIN_VALUE});
        assertThrows(NullPointerException.class, () -> SignFilter.split(null));
    }

    @Test
    void shouldMatchStreamReferenceWithoutChangingInput() {
        Random random = new Random(801L);
        for (int trial = 0; trial < 200; trial++) {
            int[] values = random.ints(random.nextInt(100), -20, 21).toArray();
            int[] original = values.clone();
            assertGroups(values, Arrays.stream(values).filter(value -> value > 0).toArray(),
                    Arrays.stream(values).filter(value -> value < 0).toArray());
            assertArrayEquals(original, values);
        }
    }

    private static void assertGroups(int[] values, int[] positive, int[] negative) {
        SignGroups groups = SignFilter.split(values);
        assertArrayEquals(positive, groups.positive());
        assertArrayEquals(negative, groups.negative());
    }
}
