package ru.bmstu.lab1.task_8;

// Задача 8. Разделить положительные и отрицательные числа, сохранив порядок; нули исключить.

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Устойчивая фильтрация целочисленного массива по знаку. */
public final class SignFilter {
    private SignFilter() {
    }

    /**
     * Выделяет положительные и отрицательные числа в два новых массива.
     *
     * @param values исходный массив, который не изменяется
     * @return массивы положительных и отрицательных значений без нулей
     * @throws NullPointerException если массив равен {@code null}
     */
    public static SignGroups split(int[] values) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        int positiveCount = 0;
        int negativeCount = 0;
        for (int value : values) {
            if (value > 0) {
                positiveCount++;
            } else if (value < 0) {
                negativeCount++;
            }
        }
        int[] positive = new int[positiveCount];
        int[] negative = new int[negativeCount];
        int positiveIndex = 0;
        int negativeIndex = 0;
        for (int value : values) {
            if (value > 0) {
                positive[positiveIndex++] = value;
            } else if (value < 0) {
                negative[negativeIndex++] = value;
            }
        }
        return new SignGroups(positive, negative);
    }

    /**
     * Читает массив и выводит положительные и отрицательные числа на отдельных строках.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SignGroups groups = split(ConsoleInput.readArray(scanner));
        ConsoleInput.printArray(groups.positive());
        ConsoleInput.printArray(groups.negative());
    }
}
