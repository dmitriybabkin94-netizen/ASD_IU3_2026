package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@ResourceLock("SYSTEM_STREAMS")
class ConsoleInputTest {
    @Test
    void shouldReadAcrossWhitespaceWithoutConsumingNextBlock() {
        try (Scanner scanner = new Scanner("3\n-5\t0 12\n2 4 9")) {
            assertArrayEquals(new int[] {-5, 0, 12}, ConsoleInput.readArray(scanner));
            assertArrayEquals(new int[] {4, 9}, ConsoleInput.readArray(scanner));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 100_000})
    void shouldRejectLengthOutsideTaskLimits(int length) {
        try (Scanner scanner = new Scanner(Integer.toString(length))) {
            assertThrows(IllegalArgumentException.class, () -> ConsoleInput.readArray(scanner));
        }
    }

    @Test
    void shouldAcceptLargestPermittedArray() {
        int length = 99_999;
        StringBuilder input = new StringBuilder().append(length);
        for (int index = 0; index < length; index++) {
            input.append(' ').append(index);
        }
        try (Scanner scanner = new Scanner(input.toString())) {
            int[] actual = ConsoleInput.readArray(scanner);
            assertEquals(length, actual.length);
            assertEquals(0, actual[0]);
            assertEquals(length - 1, actual[length - 1]);
        }
    }

    @Test
    void shouldRejectMissingOrNonIntegerData() {
        assertThrows(NullPointerException.class, () -> ConsoleInput.readArray(null));
        try (Scanner missing = new Scanner("2 1"); Scanner nonInteger = new Scanner("2 1 x")) {
            assertThrows(NoSuchElementException.class, () -> ConsoleInput.readArray(missing));
            assertThrows(InputMismatchException.class, () -> ConsoleInput.readArray(nonInteger));
        }
    }

    @Test
    void shouldPrintValuesAndEmptyArrayAsSeparateLines() {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            ConsoleInput.printArray(new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
            ConsoleInput.printArray(new int[0]);
        } finally {
            System.setOut(original);
        }
        assertEquals("-2147483648 0 2147483647\n\n", bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
        assertThrows(NullPointerException.class, () -> ConsoleInput.printArray(null));
    }
}
