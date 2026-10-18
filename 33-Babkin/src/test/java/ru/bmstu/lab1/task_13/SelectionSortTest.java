package ru.bmstu.lab1.task_13;

// Задача 13. Проверки сортировки выбором по неубыванию.

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SelectionSortTest {
    @Test
    void shouldSortMixedNumbersInPlace() {
        int[] values = {5, -2, 0, 5, 1};
        SelectionSort.sort(values);
        assertArrayEquals(new int[] {-2, 0, 1, 5, 5}, values);
    }

    @Test
    void shouldAcceptEmptySingletonSortedAndReverseSortedArrays() {
        assertSorted(new int[0]);
        assertSorted(new int[] {42});
        assertSorted(new int[] {-2, -1, 0, 2});
        assertSorted(new int[] {9, 8, 7, 2});
        assertSorted(new int[] {3, 3, 3});
    }

    @Test
    void shouldHandleIntegerLimitsAndRejectNull() {
        assertSorted(new int[] {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, -1});
        assertThrows(NullPointerException.class, () -> SelectionSort.sort(null));
    }

    @Test
    void shouldMatchStandardSortForSeededRandomArrays() {
        Random random = new Random(1301L);
        for (int trial = 0; trial < 200; trial++) {
            assertSorted(random.ints(random.nextInt(100), -1000, 1001).toArray());
        }
    }

    private static void assertSorted(int[] values) {
        int[] expected = values.clone();
        Arrays.sort(expected);
        SelectionSort.sort(values);
        assertArrayEquals(expected, values);
    }
}
