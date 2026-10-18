package ru.bmstu.lab1.task_43;

// Задача 43. Проверки устойчивого перемещения нулей в конец исходного массива.

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ZeroMoverTest {
    @Test
    void shouldMoveZerosToEndWhilePreservingNonzeroOrder() {
        int[] values = {0, 3, 0, -1, 5};

        ZeroMover.moveToEnd(values);

        assertArrayEquals(new int[] {3, -1, 5, 0, 0}, values);
    }

    @Test
    void shouldPreserveDuplicatesAndExtremeIntegers() {
        int[] values = {0, Integer.MAX_VALUE, -1, 0, Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE, 0};

        ZeroMover.moveToEnd(values);

        assertArrayEquals(new int[] {Integer.MAX_VALUE, -1, Integer.MIN_VALUE, -1,
                Integer.MAX_VALUE, 0, 0, 0, 0}, values);
    }

    @Test
    void shouldLeaveArraysWithoutZerosUnchanged() {
        int[] values = {2, -5, 2, Integer.MIN_VALUE, Integer.MAX_VALUE};

        ZeroMover.moveToEnd(values);

        assertArrayEquals(new int[] {2, -5, 2, Integer.MIN_VALUE, Integer.MAX_VALUE}, values);
    }

    @Test
    void shouldSupportAllZerosEmptyAndSingleElementArrays() {
        int[] zeros = {0, 0, 0};
        int[] empty = new int[0];
        int[] singleZero = {0};
        int[] singleNonzero = {-1};

        ZeroMover.moveToEnd(zeros);
        ZeroMover.moveToEnd(empty);
        ZeroMover.moveToEnd(singleZero);
        ZeroMover.moveToEnd(singleNonzero);

        assertArrayEquals(new int[] {0, 0, 0}, zeros);
        assertArrayEquals(new int[0], empty);
        assertArrayEquals(new int[] {0}, singleZero);
        assertArrayEquals(new int[] {-1}, singleNonzero);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> ZeroMover.moveToEnd(null));
    }

    @Test
    void shouldMatchStableFilteringReferenceForSeededInputs() {
        Random random = new Random(4301L);
        for (int trial = 0; trial < 300; trial++) {
            int[] values = random.ints(random.nextInt(100), -3, 4).toArray();
            int[] expected = zerosAfterNonzeros(values);

            ZeroMover.moveToEnd(values);

            assertArrayEquals(expected, values, "trial=" + trial);
        }
    }

    @Test
    void shouldMoveZerosInNinetyNineThousandNineHundredNinetyNineValues() {
        int[] values = new int[99_999];
        for (int index = 0; index < values.length; index++) {
            values[index] = index % 3 == 0 ? 0 : index - 50_000;
        }
        int[] expected = zerosAfterNonzeros(values);

        ZeroMover.moveToEnd(values);

        assertArrayEquals(expected, values);
    }

    private static int[] zerosAfterNonzeros(int[] values) {
        int[] nonzeros = Arrays.stream(values).filter(value -> value != 0).toArray();
        return Arrays.copyOf(nonzeros, values.length);
    }
}
