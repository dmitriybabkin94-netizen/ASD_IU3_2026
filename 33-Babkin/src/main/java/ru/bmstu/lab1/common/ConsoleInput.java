package ru.bmstu.lab1.common;

import java.util.Objects;
import java.util.Scanner;

/** Общий числовой ввод и вывод для консольных решений ЛР1. */
public final class ConsoleInput {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 99_999;

    private ConsoleInput() {
    }

    /**
     * Читает длину и элементы массива; пробелы и переводы строк равнозначны.
     *
     * @param scanner источник чисел
     * @return прочитанный массив
     * @throws IllegalArgumentException если длина вне ограничений задания
     */
    public static int[] readArray(Scanner scanner) {
        Objects.requireNonNull(scanner, "Нужен источник ввода");
        int length = scanner.nextInt();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException("Длина массива должна быть от 2 до 99999");
        }
        int[] values = new int[length];
        for (int index = 0; index < length; index++) {
            values[index] = scanner.nextInt();
        }
        return values;
    }

    /**
     * Выводит массив через пробел и завершает строку, в том числе для пустого массива.
     *
     * @param values выводимые значения
     */
    public static void printArray(int[] values) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        StringBuilder output = new StringBuilder();
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                output.append(' ');
            }
            output.append(values[index]);
        }
        System.out.println(output);
    }
}
