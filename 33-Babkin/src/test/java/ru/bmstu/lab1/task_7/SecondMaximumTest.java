package ru.bmstu.lab1.task_7;

// Задача 7. Проверки поиска второго различного максимума.

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SecondMaximumTest {
    @Test
    void shouldFindSecondMaximumInBothTwoElementOrders() {
        assertEquals(2, SecondMaximum.find(new int[] {2, 9}));
        assertEquals(2, SecondMaximum.find(new int[] {9, 2}));
    }

    @Test
    void shouldHandleNegativesRepeatedMaximumAndIntegerLimits() {
        assertEquals(-5, SecondMaximum.find(new int[] {-8, -2, -5, -2}));
        assertEquals(4, SecondMaximum.find(new int[] {7, 4, 7, 3, 4}));
        assertEquals(Integer.MIN_VALUE,
                SecondMaximum.find(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}));
        assertEquals(0, SecondMaximum.find(new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectInputsWithoutTwoDistinctValues() {
        assertThrows(NullPointerException.class, () -> SecondMaximum.find(null));
        assertThrows(IllegalArgumentException.class, () -> SecondMaximum.find(new int[0]));
        assertThrows(IllegalArgumentException.class, () -> SecondMaximum.find(new int[] {1}));
        assertThrows(IllegalArgumentException.class, () -> SecondMaximum.find(new int[] {5, 5, 5}));
    }

    @Test
    void shouldMatchSortedDistinctReferenceWithoutChangingInput() {
        Random random = new Random(701L);
        for (int trial = 0; trial < 200; trial++) {
            int[] values = random.ints(2 + random.nextInt(80), -100, 101).toArray();
            values[1] = values[0] == 100 ? 99 : values[0] + 1;
            int[] original = values.clone();
            int[] distinct = Arrays.stream(values).distinct().sorted().toArray();
            assertEquals(distinct[distinct.length - 2], SecondMaximum.find(values));
            assertArrayEquals(original, values);
        }
    }
}
