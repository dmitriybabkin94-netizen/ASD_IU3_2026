package ru.bmstu.lab1.task_7;

// Задача 7. Найти второй по величине различный элемент массива без сортировки.

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Поиск второго различного максимума за один проход. */
public final class SecondMaximum {
    private SecondMaximum() {
    }

    /**
     * Находит наибольшее значение, строго меньшее максимума массива.
     *
     * @param values исходный массив, который не изменяется
     * @return второй различный максимум
     * @throws NullPointerException если массив равен {@code null}
     * @throws IllegalArgumentException если в массиве меньше двух различных значений
     */
    public static int find(int[] values) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        if (values.length < 2) {
            throw new IllegalArgumentException("Нужно не менее двух различных значений");
        }
        int maximum = values[0];
        int secondMaximum = 0;
        boolean hasSecond = false;
        for (int index = 1; index < values.length; index++) {
            int value = values[index];
            if (value > maximum) {
                secondMaximum = maximum;
                maximum = value;
                hasSecond = true;
            } else if (value < maximum && (!hasSecond || value > secondMaximum)) {
                secondMaximum = value;
                hasSecond = true;
            }
        }
        if (!hasSecond) {
            throw new IllegalArgumentException("Нужно не менее двух различных значений");
        }
        return secondMaximum;
    }

    /**
     * Читает длину и элементы массива и выводит второй различный максимум.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] values = ConsoleInput.readArray(scanner);
        System.out.println(find(values));
    }
}
