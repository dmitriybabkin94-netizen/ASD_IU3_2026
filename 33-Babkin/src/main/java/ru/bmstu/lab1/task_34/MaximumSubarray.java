package ru.bmstu.lab1.task_34;

// Задача 34. Найти максимальную сумму непрерывного подмассива алгоритмом Кадане за O(n).

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Алгоритм Кадане для непустого подмассива с накоплением суммы в long. */
public final class MaximumSubarray {
    private MaximumSubarray() {
    }

    /**
     * Находит максимальную сумму непустого непрерывного участка, не изменяя массив.
     *
     * @param values непустой исходный массив
     * @return максимальная сумма; для отрицательных чисел — наибольший элемент
     * @throws IllegalArgumentException если массив пуст
     */
    public static long maxSum(int[] values) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        if (values.length == 0) {
            throw new IllegalArgumentException("Нужен непустой массив");
        }
        long endingHere = values[0];
        long best = values[0];
        for (int index = 1; index < values.length; index++) {
            // Лучший участок с концом здесь либо начинается заново, либо продолжает предыдущий.
            endingHere = Math.max(values[index], endingHere + values[index]);
            best = Math.max(best, endingHere);
        }
        return best;
    }

    /**
     * Читает массив и выводит максимальную сумму непрерывного участка.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(maxSum(ConsoleInput.readArray(scanner)));
    }
}
