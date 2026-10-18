package ru.bmstu.lab1.task_43;

// Задача 43. Переместить нули в конец на месте, сохранив порядок остальных элементов.

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Устойчивое уплотнение ненулевых элементов с заполнением хвоста нулями. */
public final class ZeroMover {
    private ZeroMover() {
    }

    /**
     * Перемещает все нули в конец исходного массива за O(n) времени и O(1) памяти.
     *
     * @param values изменяемый массив
     */
    public static void moveToEnd(int[] values) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        int writeIndex = 0;
        for (int value : values) {
            if (value != 0) {
                values[writeIndex++] = value;
            }
        }
        while (writeIndex < values.length) {
            values[writeIndex++] = 0;
        }
    }

    /**
     * Читает массив и выводит его после перемещения нулей.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] values = ConsoleInput.readArray(scanner);
        moveToEnd(values);
        ConsoleInput.printArray(values);
    }
}
