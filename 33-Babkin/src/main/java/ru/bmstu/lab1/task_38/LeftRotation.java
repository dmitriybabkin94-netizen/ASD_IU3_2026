package ru.bmstu.lab1.task_38;

// Задача 38. Циклически сдвинуть массив влево тройным разворотом за O(n) времени и O(1) памяти.

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Циклический сдвиг влево через разворот двух частей и всего массива. */
public final class LeftRotation {
    private LeftRotation() {
    }

    /**
     * Сдвигает массив на месте; полные обороты не меняют расположение элементов.
     *
     * @param values изменяемый массив
     * @param distance неотрицательное число позиций сдвига
     * @throws IllegalArgumentException если сдвиг отрицательный
     */
    public static void rotate(int[] values, long distance) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        if (distance < 0) {
            throw new IllegalArgumentException("Сдвиг должен быть неотрицательным");
        }
        if (values.length == 0) {
            return;
        }
        int shift = (int) (distance % values.length);
        if (shift == 0) {
            return;
        }
        // Если массив состоит из частей A и B, тройной разворот превращает AB в BA.
        reverse(values, 0, shift - 1);
        reverse(values, shift, values.length - 1);
        reverse(values, 0, values.length - 1);
    }

    private static void reverse(int[] values, int left, int right) {
        while (left < right) {
            int temporary = values[left];
            values[left++] = values[right];
            values[right--] = temporary;
        }
    }

    /**
     * Читает длину, элементы, затем число позиций сдвига и выводит изменённый массив.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] values = ConsoleInput.readArray(scanner);
        rotate(values, scanner.nextLong());
        ConsoleInput.printArray(values);
    }
}
