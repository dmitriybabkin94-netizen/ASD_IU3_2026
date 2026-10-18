package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.bmstu.lab1.task_13.SelectionSort;
import ru.bmstu.lab1.task_19.SortedMerge;
import ru.bmstu.lab1.task_34.MaximumSubarray;
import ru.bmstu.lab1.task_38.LeftRotation;
import ru.bmstu.lab1.task_43.ZeroMover;
import ru.bmstu.lab1.task_7.SecondMaximum;
import ru.bmstu.lab1.task_8.SignFilter;

@ResourceLock("SYSTEM_STREAMS")
class TaskConsoleTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("consoleCases")
    void shouldReadStandardInputAndPrintExactResult(
            String name, Consumer<String[]> main, String input, String expected) {
        assertEquals(expected, runMain(main, input));
    }

    private static Stream<Arguments> consoleCases() {
        return Stream.of(
                task("7: второй максимум", SecondMaximum::main, "4\n5 2 9 1", "5\n"),
                task("8: оба знака", SignFilter::main, "6\n0 3 -2 0 -7 5", "3 5\n-2 -7\n"),
                task("8: пустые группы", SignFilter::main, "2\n0 0", "\n\n"),
                task("13: сортировка", SelectionSort::main, "5\n5 -2 0 5 1", "-2 0 1 5 5\n"),
                task("19: слияние", SortedMerge::main, "3\n1 4 7\n3\n2 4 8", "1 2 4 4 7 8\n"),
                task("34: смешанные знаки", MaximumSubarray::main, "5\n-2 3 -1 4 -5", "6\n"),
                task("34: все отрицательные", MaximumSubarray::main, "3\n-5 -2 -8", "-2\n"),
                task("38: сдвиг больше длины", LeftRotation::main, "5\n1 2 3 4 5\n7", "3 4 5 1 2\n"),
                task("43: порядок ненулевых", ZeroMover::main, "5\n0 3 0 -1 5", "3 -1 5 0 0\n"));
    }

    private static Arguments task(String name, Consumer<String[]> main, String input, String expected) {
        return Arguments.of(name, main, input, expected);
    }

    private static String runMain(Consumer<String[]> main, String input) {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream captured = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(captured);
            main.accept(new String[0]);
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
        return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
