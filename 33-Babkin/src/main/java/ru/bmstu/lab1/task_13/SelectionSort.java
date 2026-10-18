package ru.bmstu.lab1.task_13;

// Задача 13. Реализовать сортировку выбором по неубыванию без встроенной сортировки.

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Сортировка массива выбором минимального элемента на каждом шаге. */
public final class SelectionSort {
    private SelectionSort() {
    }

    /**
     * Сортирует массив по неубыванию на месте с постоянной дополнительной памятью.
     *
     * @param values массив, который нужно отсортировать
     * @throws NullPointerException если массив равен {@code null}
     */
    public static void sort(int[] values) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        for (int position = 0; position < values.length - 1; position++) {
            int minimumIndex = position;
            for (int index = position + 1; index < values.length; index++) {
                if (values[index] < values[minimumIndex]) {
                    minimumIndex = index;
                }
            }
            int temporary = values[position];
            values[position] = values[minimumIndex];
            values[minimumIndex] = temporary;
        }
    }

    /**
     * Читает массив, сортирует его выбором и выводит результат.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] values = ConsoleInput.readArray(scanner);
        sort(values);
        ConsoleInput.printArray(values);
    }
}
